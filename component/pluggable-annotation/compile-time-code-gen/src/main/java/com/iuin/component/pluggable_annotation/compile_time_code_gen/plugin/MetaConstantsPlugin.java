package com.iuin.component.pluggable_annotation.compile_time_code_gen.plugin;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.api.JavacTaskImpl;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;

public class MetaConstantsPlugin implements Plugin {
    @Override
    public String getName() {
        return "MetaConstantsPlugin";
    }

    @Override
    public void init(JavacTask task, String... args) {
        Context context = ((JavacTaskImpl) task).getContext();
        com.sun.source.util.Trees trees = com.sun.source.util.Trees.instance(task);
        TreeMaker maker = TreeMaker.instance(context);
        Names names = Names.instance(context);

        task.addTaskListener(new TaskListener() {
            @Override
            public void finished(TaskEvent e) {
                if (e.getKind() != TaskEvent.Kind.ENTER) return;
                JCCompilationUnit cu = (JCCompilationUnit) e.getCompilationUnit();
                if (cu == null || cu.defs == null) return;
                for (JCTree def : cu.defs) {
                    if (def instanceof JCClassDecl) {
                        JCClassDecl classDecl = (JCClassDecl) def;
                        if (!hasAnnotation(classDecl, ClassSignatureConstants.class.getName())) continue;
                        String innerName = "Meta";
                        String fullBinary = buildFullBinaryName(classDecl);
                        String simpleBinary = buildSimpleBinaryName(fullBinary);
                        injectInnerMeta(maker, names, classDecl, innerName, simpleBinary, fullBinary);
                    }
                }
            }
        });
    }

    private boolean hasAnnotation(JCClassDecl decl, String annFqcn) {
        for (JCAnnotation a : decl.getModifiers().getAnnotations()) {
            if (a.annotationType != null) {
                String name = a.annotationType.toString();
                if (name.equals(annFqcn) || name.endsWith("ClassSignatureConstants")) return true;
            }
        }
        return false;
    }

    private void injectInnerMeta(TreeMaker maker, Names names, JCClassDecl owner, String innerName, String simpleBinary, String fullBinary) {
        for (JCTree d : owner.defs) {
            if (d instanceof JCClassDecl && ((JCClassDecl) d).name.contentEquals(innerName)) {
                return;
            }
        }
        // public static final class Meta { ... }
        JCModifiers mods = maker.Modifiers(com.sun.tools.javac.code.Flags.PUBLIC | com.sun.tools.javac.code.Flags.STATIC | com.sun.tools.javac.code.Flags.FINAL);
        JCClassDecl meta = maker.ClassDef(mods, names.fromString(innerName), List.nil(), null, List.nil(), List.nil());

        // private Meta() {}
        JCMethodDecl ctor = maker.MethodDef(
                maker.Modifiers(com.sun.tools.javac.code.Flags.PRIVATE),
                names.fromString("<init>"),
                null,
                List.nil(),
                List.nil(),
                List.nil(),
                maker.Block(0, List.nil()),
                null
        );

        // public static final String SIMPLE_CLASS_NAME = "...";
        JCVariableDecl simple = constString(maker, names, "SIMPLE_CLASS_NAME", simpleBinary);
        // public static final String CLASS_NAME = "...";
        JCVariableDecl full = constString(maker, names, "CLASS_NAME", fullBinary);

        meta.defs = List.of(ctor, simple, full);
        owner.defs = owner.defs.append(meta);
    }

    private JCVariableDecl constString(TreeMaker maker, Names names, String name, String value) {
        JCModifiers mods = maker.Modifiers(com.sun.tools.javac.code.Flags.PUBLIC | com.sun.tools.javac.code.Flags.STATIC | com.sun.tools.javac.code.Flags.FINAL);
        JCExpression type = maker.Ident(names.fromString("String"));
        JCExpression init = maker.Literal(value);
        return maker.VarDef(mods, names.fromString(name), type, init);
    }

    private String buildSimpleBinaryName(String fullBinary) {
        int idx = fullBinary.lastIndexOf('.') + 1;
        return idx > 0 ? fullBinary.substring(idx) : fullBinary;
    }

    private String buildFullBinaryName(JCClassDecl decl) {
        if (decl.sym != null && decl.sym.flatName() != null) {
            return decl.sym.flatName().toString();
        }
        // Fallback: package + simple
        return decl.name.toString();
    }

    
}
