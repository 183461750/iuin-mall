package com.iuin.component.pluggable_annotation.compile_time_code_gen.lombok;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import lombok.core.AnnotationValues;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.JavacTreeMaker;

import javax.tools.Diagnostic;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Lombok处理器，用于处理@ClassSignatureConstants注解
 * 使用Lombok的AST转换机制在编译期生成常量
 */
public class ClassSignatureConstantsHandler extends JavacAnnotationHandler<ClassSignatureConstants> {

    @Override
    public void handle(AnnotationValues<ClassSignatureConstants> annotationValues, JCTree.JCAnnotation annotation, JavacNode annotationNode) {
        try {
            // 确保注解应用在类上
            if (annotationNode.up() == null || annotationNode.up().getKind() != JCTree.Tag.CLASSDEF) {
                annotationNode.addWarning("@ClassSignatureConstants 只能应用于类");
                return;
            }

            JavacNode typeNode = annotationNode.up();
            JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) typeNode.get();
            Context context = annotationNode.getContext();
            TreeMaker maker = TreeMaker.instance(context);
            Names names = Names.instance(context);

            // 获取注解参数
            String innerClassName = annotationValues.getAs(String.class, "innerClassName");
            String prefix = annotationValues.getAs(String.class, "prefix");
            String suffix = annotationValues.getAs(String.class, "suffix");
            boolean asEnum = annotationValues.getAs(Boolean.class, "asEnum");
            boolean onlyPublicMethods = annotationValues.getAs(Boolean.class, "onlyPublicMethods");
            boolean includeInheritedMethods = annotationValues.getAs(Boolean.class, "includeInheritedMethods");
            
            // 添加调试信息
            annotationNode.getMessager().printMessage(
                Diagnostic.Kind.NOTE, 
                "处理类: " + classDecl.name.toString() + ", 生成内部类: " + innerClassName
            );

            // 生成内部类/枚举
            JCTree typeDef;
            if (asEnum) {
                typeDef = createEnumDefinition(innerClassName, classDecl, maker, names, prefix, suffix, onlyPublicMethods);
            } else {
                typeDef = createConstantsClassDefinition(innerClassName, classDecl, maker, names, prefix, suffix, onlyPublicMethods);
            }

            // 将生成的类型添加到原始类中
            classDecl.defs = classDecl.defs.append(typeDef);

            // 标记注解已处理
            annotationNode.setHandled();
        } catch (Exception e) {
            annotationNode.addWarning("处理@ClassSignatureConstants时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建常量类定义
     */
    private JCTree.JCClassDecl createConstantsClassDefinition(String innerClassName, JCTree.JCClassDecl outerClass, 
                                                            TreeMaker maker, Names names, String prefix, String suffix, 
                                                            boolean onlyPublicMethods) {
        // 创建内部类
        JCTree.JCClassDecl innerClass = maker.ClassDef(
                maker.Modifiers(Flags.PUBLIC | Flags.STATIC | Flags.FINAL),
                names.fromString(innerClassName),
                List.nil(), // 类型参数
                null, // 扩展的类
                List.nil(), // 实现的接口
                List.nil()  // 类成员
        );

        // 添加类名常量
        String className = outerClass.sym.getQualifiedName().toString();
        String simpleClassName = outerClass.name.toString();
        
        innerClass.defs = innerClass.defs.append(createStringConstant(maker, names, prefix + "CLASS_NAME" + suffix, className));
        innerClass.defs = innerClass.defs.append(createStringConstant(maker, names, prefix + "SIMPLE_CLASS_NAME" + suffix, simpleClassName));

        // 添加方法签名常量
        for (JCTree def : outerClass.defs) {
            if (def.getKind() == JCTree.Tag.METHODDEF) {
                JCTree.JCMethodDecl methodDef = (JCTree.JCMethodDecl) def;
                
                // 过滤构造方法
                if (methodDef.name.toString().equals("<init>") || methodDef.name.toString().equals("<clinit>")) {
                    continue;
                }
                
                // 根据可见性过滤
                if (!onlyPublicMethods || methodDef.mods.getFlags() == Flags.PUBLIC) {
                    String methodName = methodDef.name.toString();
                    String methodSignature = generateMethodSignature(methodDef);
                    String constantName = prefix + toConstantName(methodName) + suffix;
                    
                    innerClass.defs = innerClass.defs.append(createStringConstant(maker, names, constantName, methodSignature));
                }
            }
        }

        return innerClass;
    }

    /**
     * 创建枚举定义
     */
    private JCTree.JCClassDecl createEnumDefinition(String enumName, JCTree.JCClassDecl outerClass, 
                                                 TreeMaker maker, Names names, String prefix, String suffix, 
                                                 boolean onlyPublicMethods) {
        // 创建枚举类
        JCTree.JCClassDecl enumDecl = maker.EnumDef(
                maker.Modifiers(Flags.PUBLIC | Flags.STATIC),
                names.fromString(enumName),
                List.nil(), // 类型参数
                null, // 实现的接口
                List.nil()  // 枚举常量
        );

        // 添加value字段
        enumDecl.defs = enumDecl.defs.append(
                maker.VarDef(
                        maker.Modifiers(Flags.PRIVATE | Flags.FINAL),
                        names.fromString("value"),
                        maker.Ident(names.fromString("String")),
                        null
                )
        );

        // 添加构造方法
        enumDecl.defs = enumDecl.defs.append(
                maker.MethodDef(
                        maker.Modifiers(Flags.PRIVATE),
                        names.fromString("<init>"),
                        maker.TypeIdent(JCTree.Tag.VOID),
                        List.nil(),
                        List.of(maker.VarDef(
                                maker.Modifiers(0),
                                names.fromString("value"),
                                maker.Ident(names.fromString("String")),
                                null
                        )),
                        List.nil(),
                        maker.Block(
                                0,
                                List.of(maker.Exec(
                                        maker.Assign(
                                                maker.Select(maker.This(names.fromString(enumName)), names.fromString("value")),
                                                maker.Ident(names.fromString("value"))
                                        )
                                ))
                        ),
                        null
                )
        );

        // 添加getValue方法
        enumDecl.defs = enumDecl.defs.append(
                maker.MethodDef(
                        maker.Modifiers(Flags.PUBLIC),
                        names.fromString("getValue"),
                        maker.Ident(names.fromString("String")),
                        List.nil(),
                        List.nil(),
                        List.nil(),
                        maker.Block(
                                0,
                                List.of(maker.Return(
                                        maker.Select(maker.This(names.fromString(enumName)), names.fromString("value"))
                                ))
                        ),
                        null
                )
        );

        // 添加类名枚举常量
        String className = outerClass.sym.getQualifiedName().toString();
        String simpleClassName = outerClass.name.toString();
        
        String classNameEnumName = toEnumName(prefix + "CLASS_NAME" + suffix);
        String simpleClassNameEnumName = toEnumName(prefix + "SIMPLE_CLASS_NAME" + suffix);
        
        enumDecl.defs = enumDecl.defs.prepend(
                maker.AnnotatedType(
                        List.nil(),
                        maker.NewClass(
                                null,
                                List.nil(),
                                maker.Ident(names.fromString(classNameEnumName)),
                                List.of(maker.Literal(className)),
                                null
                        )
                )
        );

        enumDecl.defs = enumDecl.defs.prepend(
                maker.AnnotatedType(
                        List.nil(),
                        maker.NewClass(
                                null,
                                List.nil(),
                                maker.Ident(names.fromString(simpleClassNameEnumName)),
                                List.of(maker.Literal(simpleClassName)),
                                null
                        )
                )
        );

        // 添加方法签名枚举常量
        for (JCTree def : outerClass.defs) {
            if (def.getKind() == JCTree.Tag.METHODDEF) {
                JCTree.JCMethodDecl methodDef = (JCTree.JCMethodDecl) def;
                
                // 过滤构造方法
                if (methodDef.name.toString().equals("<init>") || methodDef.name.toString().equals("<clinit>")) {
                    continue;
                }
                
                // 根据可见性过滤
                if (!onlyPublicMethods || methodDef.mods.getFlags() == Flags.PUBLIC) {
                    String methodName = methodDef.name.toString();
                    String methodSignature = generateMethodSignature(methodDef);
                    String enumConstantName = toEnumName(prefix + toConstantName(methodName) + suffix);
                    
                    enumDecl.defs = enumDecl.defs.prepend(
                            maker.AnnotatedType(
                                    List.nil(),
                                    maker.NewClass(
                                            null,
                                            List.nil(),
                                            maker.Ident(names.fromString(enumConstantName)),
                                            List.of(maker.Literal(methodSignature)),
                                            null
                                    )
                            )
                    );
                }
            }
        }

        return enumDecl;
    }

    /**
     * 创建字符串常量字段
     */
    private JCTree.JCVariableDecl createStringConstant(TreeMaker maker, Names names, String name, String value) {
        return maker.VarDef(
                maker.Modifiers(Flags.PUBLIC | Flags.STATIC | Flags.FINAL),
                names.fromString(name),
                maker.Ident(names.fromString("String")),
                maker.Literal(value)
        );
    }

    /**
     * 生成方法签名
     */
    private String generateMethodSignature(JCTree.JCMethodDecl method) {
        String methodName = method.name.toString();
        String returnType = method.restype.toString();
        
        // 构建参数类型字符串
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < method.params.size(); i++) {
            if (i > 0) {
                params.append(", ");
            }
            params.append(method.params.get(i).vartype.toString());
        }
        
        return returnType + " " + methodName + "(" + params + ")";
    }

    /**
     * 将方法名转换为常量名格式
     */
    private String toConstantName(String name) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (i > 0 && Character.isUpperCase(c) && !Character.isUpperCase(name.charAt(i-1))) {
                result.append('_');
            }
            result.append(Character.toUpperCase(c));
        }
        return result.toString();
    }

    /**
     * 将常量名转换为枚举名格式
     */
    private String toEnumName(String name) {
        return name.replaceAll("[^a-zA-Z0-9_]", "_");
    }
}
