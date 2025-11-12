package com.iuin.annotation.handler;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCModifiers;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.tree.JCTree.JCTypeParameter;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import lombok.core.AST;
import lombok.core.AnnotationValues;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.JavacTreeMaker;
import lombok.javac.handlers.JavacHandlerUtil;
import lombok.javac.handlers.JavacHandlerUtil.MemberExistsResult;
import com.iuin.annotation.ClassConstant;

public class ClassConstantHandler extends JavacAnnotationHandler<ClassConstant> {
    private static final String INNER_CLASS_NAME = "Clazz";

    @Override
    public void handle(AnnotationValues<ClassConstant> annotation, JCTree.JCAnnotation ast, JavacNode annotationNode) {
        // 删除注解，因为它只在编译期使用
        JavacHandlerUtil.deleteAnnotationIfNeccessary(annotationNode, ClassConstant.class);
        
        // 获取注解所在的类节点
        JavacNode typeNode = annotationNode.up();
        
        // 检查是否为类
        if (typeNode.getKind() != AST.Kind.TYPE) {
            annotationNode.addError("@ClassConstant 只能用于类上");
            return;
        }
        
        // 检查是否为非静态嵌套类
        if (!JavacHandlerUtil.isStaticAllowed(typeNode)) {
            annotationNode.addError("@ClassConstant 不支持非静态嵌套类");
            return;
        }
        
        // 生成内部类
        generateClassConstantInnerClass(typeNode, annotationNode);
    }

    private void generateClassConstantInnerClass(JavacNode typeNode, JavacNode errorNode) {
        JavacTreeMaker maker = typeNode.getTreeMaker();
        JCModifiers mods = maker.Modifiers(Flags.PUBLIC | Flags.STATIC | Flags.FINAL);
        
        // 获取内部类名称
        Name innerClassName = typeNode.toName(INNER_CLASS_NAME);
        
        // 检查内部类是否已存在
        JavacNode innerType = findInnerClass(typeNode, INNER_CLASS_NAME);
        boolean needGenerateConstructor = false;
        
        if (innerType == null) {
            // 创建新的内部类
            JCClassDecl innerClass = maker.ClassDef(
                    mods, 
                    innerClassName, 
                    List.<JCTypeParameter>nil(), 
                    null, 
                    List.<JCExpression>nil(), 
                    List.<JCTree>nil()
            );
            innerType = JavacHandlerUtil.injectType(typeNode, innerClass);
            JavacHandlerUtil.recursiveSetGeneratedBy(innerClass, errorNode);
            needGenerateConstructor = true;
        } else {
            // 检查现有内部类是否符合要求
            JCClassDecl innerClassDecl = (JCClassDecl) innerType.get();
            long flags = innerClassDecl.getModifiers().flags;
            if ((flags & Flags.STATIC) == 0 || (flags & Flags.FINAL) == 0) {
                errorNode.addError("现有的 " + INNER_CLASS_NAME + " 必须声明为 'public static final class'");
                return;
            }
            needGenerateConstructor = constructorExists(innerType) == MemberExistsResult.NOT_EXISTS;
        }
        
        // 生成私有构造函数
        if (needGenerateConstructor) {
            JCModifiers constructorMods = maker.Modifiers(Flags.PRIVATE);
            JCBlock constructorBody = maker.Block(0L, List.<JCStatement>of());
            JCMethodDecl constructor = maker.MethodDef(
                    constructorMods, 
                    typeNode.toName("<init>"), 
                    null, 
                    List.<JCTypeParameter>nil(), 
                    List.<JCVariableDecl>nil(), 
                    List.<JCExpression>nil(), 
                    constructorBody, 
                    null
            );
            JavacHandlerUtil.recursiveSetGeneratedBy(constructor, errorNode);
            JavacHandlerUtil.injectMethod(innerType, constructor);
        }
        
        // 生成类名常量
        String className = typeNode.getName();
        String constantName = convertToConstantName(className);
        
        // 检查常量是否已存在
        if (fieldExists(constantName, innerType) != MemberExistsResult.NOT_EXISTS) {
            return;
        }
        
        // 创建常量字段
        JCModifiers constantMods = maker.Modifiers(Flags.PUBLIC | Flags.STATIC | Flags.FINAL);
        JCExpression stringType = JavacHandlerUtil.chainDotsString(typeNode, "java.lang.String");
        JCExpression initializer = maker.Literal(className);
        
        JCVariableDecl constantField = maker.VarDef(
                constantMods, 
                typeNode.toName(constantName), 
                stringType, 
                initializer
        );
        
        JavacHandlerUtil.setGeneratedBy(constantField, errorNode);
        JavacHandlerUtil.injectField(innerType, constantField, false, true);
    }
    
    /**
     * 将类名转换为常量名格式（全大写，下划线分隔）
     * 例如：UserDTO -> USER_DTO
     */
    private String convertToConstantName(String className) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < className.length(); i++) {
            char c = className.charAt(i);
            if (i > 0 && Character.isUpperCase(c)) {
                // 检查是否是连续大写字母的最后一个
                boolean isLastUpperCase = i + 1 < className.length() && !Character.isUpperCase(className.charAt(i + 1));
                if (isLastUpperCase) {
                    result.append('_');
                } else if (i - 1 > 0 && !Character.isUpperCase(className.charAt(i - 1))) {
                    // 前面是小写字母，当前是大写字母，添加下划线
                    result.append('_');
                }
            }
            result.append(Character.toUpperCase(c));
        }
        return result.toString();
    }
    
    private JavacNode findInnerClass(JavacNode parent, String name) {
        for (JavacNode child : parent.down()) {
            if (child.getKind() == AST.Kind.TYPE && child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }
    
    private MemberExistsResult fieldExists(String name, JavacNode typeNode) {
        return JavacHandlerUtil.fieldExists(name, typeNode);
    }
    
    private MemberExistsResult constructorExists(JavacNode typeNode) {
        return JavacHandlerUtil.constructorExists(List.<String>nil(), typeNode);
    }
}