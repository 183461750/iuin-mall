package com.iuin.component.pluggable_annotation.compile_time_code_gen.handler;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
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

public class HandleClassSignatureConstants extends JavacAnnotationHandler<ClassSignatureConstants> {

    @Override
    public void handle(AnnotationValues<ClassSignatureConstants> annotation, JCTree.JCAnnotation ast, JavacNode annotationNode) {
        JavacHandlerUtil.deleteAnnotationIfNeccessary(annotationNode, ClassSignatureConstants.class);

        JavacNode typeNode = annotationNode.up();
        if (typeNode.getKind() != AST.Kind.TYPE) {
            annotationNode.addError("@ClassSignatureConstants 只能用于类上");
            return;
        }
        if (!JavacHandlerUtil.isStaticAllowed(typeNode)) {
            annotationNode.addError("@ClassSignatureConstants 不支持非静态嵌套类");
            return;
        }

        ClassSignatureConstants cfg = annotation.getInstance();
        String innerName = cfg != null && cfg.innerClassName() != null && !cfg.innerClassName().isBlank() ? cfg.innerClassName() : "Meta";

        generateMeta(typeNode, annotationNode, innerName);
    }

    private void generateMeta(JavacNode typeNode, JavacNode errorNode, String innerName) {
        JavacTreeMaker maker = typeNode.getTreeMaker();
        JCModifiers mods = maker.Modifiers(Flags.PUBLIC | Flags.STATIC | Flags.FINAL);

        Name metaName = typeNode.toName(innerName);
        JavacNode innerType = findInnerClass(typeNode, innerName);
        boolean needConstructor = false;

        if (innerType == null) {
            JCClassDecl meta = maker.ClassDef(
                mods,
                metaName,
                List.<JCTypeParameter>nil(),
                null,
                List.<JCExpression>nil(),
                List.<JCTree>nil()
            );
            innerType = JavacHandlerUtil.injectType(typeNode, meta);
            JavacHandlerUtil.recursiveSetGeneratedBy(meta, errorNode);
            needConstructor = true;
        } else {
            JCClassDecl decl = (JCClassDecl) innerType.get();
            long flags = decl.getModifiers().flags;
            if ((flags & Flags.STATIC) == 0 || (flags & Flags.FINAL) == 0) {
                errorNode.addError("现有的 " + innerName + " 必须声明为 'public static final class'");
                return;
            }
            needConstructor = constructorExists(innerType) == MemberExistsResult.NOT_EXISTS;
        }

        if (needConstructor) {
            JCModifiers cmods = maker.Modifiers(Flags.PRIVATE);
            JCBlock body = maker.Block(0L, List.<JCStatement>of());
            JCMethodDecl c = maker.MethodDef(
                cmods,
                typeNode.toName("<init>"),
                null,
                List.<JCTypeParameter>nil(),
                List.<JCVariableDecl>nil(),
                List.<JCExpression>nil(),
                body,
                null
            );
            JavacHandlerUtil.recursiveSetGeneratedBy(c, errorNode);
            JavacHandlerUtil.injectMethod(innerType, c);
        }

        String simpleBinary = buildSimpleBinaryName(typeNode);
        String fullBinary = buildFullBinaryName(typeNode);

        injectStringConstant(innerType, errorNode, "SIMPLE_CLASS_NAME", simpleBinary);
        injectStringConstant(innerType, errorNode, "CLASS_NAME", fullBinary);
    }

    private void injectStringConstant(JavacNode typeNode, JavacNode errorNode, String name, String value) {
        if (fieldExists(name, typeNode) != MemberExistsResult.NOT_EXISTS) return;
        JavacTreeMaker maker = typeNode.getTreeMaker();
        JCModifiers fmods = maker.Modifiers(Flags.PUBLIC | Flags.STATIC | Flags.FINAL);
        JCExpression stringType = JavacHandlerUtil.chainDotsString(typeNode, "java.lang.String");
        JCExpression init = maker.Literal(value);
        JCVariableDecl f = maker.VarDef(fmods, typeNode.toName(name), stringType, init);
        JavacHandlerUtil.setGeneratedBy(f, errorNode);
        JavacHandlerUtil.injectField(typeNode, f, false, true);
    }

    private String buildSimpleBinaryName(JavacNode typeNode) {
        String nested = buildNestedBinaryName(typeNode);
        int idx = nested.lastIndexOf('.') + 1;
        return idx > 0 ? nested.substring(idx) : nested;
    }

    private String buildFullBinaryName(JavacNode typeNode) {
        return buildNestedBinaryName(typeNode);
    }

    private String buildNestedBinaryName(JavacNode typeNode) {
        StringBuilder sb = new StringBuilder();
        JCCompilationUnit cu = (JCCompilationUnit) typeNode.top().get();
        String pkg = cu == null || cu.pid == null ? "" : cu.pid.toString();
        if (pkg != null && !pkg.isEmpty()) {
            sb.append(pkg).append('.');
        }
        java.util.Deque<String> names = new java.util.ArrayDeque<>();
        JavacNode n = typeNode;
        while (n != null && n.getKind() == AST.Kind.TYPE) {
            names.push(n.getName());
            n = n.up();
            if (n == null || n.getKind() != AST.Kind.TYPE) break;
        }
        boolean first = true;
        while (!names.isEmpty()) {
            String s = names.removeFirst();
            if (first) {
                sb.append(s);
                first = false;
            } else {
                sb.append('$').append(s);
            }
        }
        return sb.toString();
    }

    private JavacNode findInnerClass(JavacNode parent, String name) {
        for (JavacNode child : parent.down()) {
            if (child.getKind() == AST.Kind.TYPE && child.getName().equals(name)) return child;
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

