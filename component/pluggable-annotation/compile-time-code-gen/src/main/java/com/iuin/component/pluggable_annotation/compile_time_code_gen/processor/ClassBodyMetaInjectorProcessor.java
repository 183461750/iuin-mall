package com.iuin.component.pluggable_annotation.compile_time_code_gen.processor;

import com.google.auto.service.AutoService;
import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassMetaConstants;
import com.sun.source.util.Trees;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

@AutoService(Processor.class)
public class ClassBodyMetaInjectorProcessor extends AbstractProcessor {
    private Trees trees;
    private Object treeMaker;
    private Object names;

    @Override
    public synchronized void init(javax.annotation.processing.ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        trees = Trees.instance(processingEnv);
        try {
            addOpensForProcessor();
            Class<?> jpe = Class.forName("com.sun.tools.javac.processing.JavacProcessingEnvironment");
            Method getContext = jpe.getDeclaredMethod("getContext");
            Object ctx = getContext.invoke(processingEnv);
            Class<?> tm = Class.forName("com.sun.tools.javac.tree.TreeMaker");
            Class<?> ns = Class.forName("com.sun.tools.javac.util.Names");
            Method tmInst = tm.getDeclaredMethod("instance", Class.forName("com.sun.tools.javac.util.Context"));
            Method nsInst = ns.getDeclaredMethod("instance", Class.forName("com.sun.tools.javac.util.Context"));
            treeMaker = tmInst.invoke(null, ctx);
            names = nsInst.invoke(null, ctx);
        } catch (Throwable t) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "init failed: " + t.getMessage());
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(ClassMetaConstants.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty())
            return false;
        Elements el = processingEnv.getElementUtils();
        for (Element e : roundEnv.getElementsAnnotatedWith(ClassMetaConstants.class)) {
            try {
                Object tree = trees.getTree(e);
                if (tree == null)
                    continue;
                Class<?> jcClassDecl = Class.forName("com.sun.tools.javac.tree.JCTree$JCClassDecl");
                if (!jcClassDecl.isInstance(tree))
                    continue;
                TypeElement te = (TypeElement) e;
                ClassMetaConstants cfg = e.getAnnotation(ClassMetaConstants.class);
                String innerName = cfg != null && !cfg.innerClassName().isBlank() ? cfg.innerClassName() : "Metas";
                String full = el.getBinaryName(te).toString();
                String simple = stripPackage(full);
                if (!isStaticAllowed(jcClassDecl.cast(tree))) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            "@ClassMetaConstants 仅允许用于顶层类或静态嵌套类", e);
                    continue;
                }
                injectInnerMeta(tree, innerName, simple, full);
                removeAnnotation(jcClassDecl.cast(tree));
            } catch (Throwable t) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "process failed: " + t.getMessage(), e);
            }
        }
        return true;
    }

    private void injectInnerMeta(Object owner, String innerName, String simpleBinary, String fullBinary)
            throws Exception {
        Class<?> tmCls = Class.forName("com.sun.tools.javac.tree.TreeMaker");
        Class<?> modsCls = Class.forName("com.sun.tools.javac.tree.JCTree$JCModifiers");
        Class<?> listCls = Class.forName("com.sun.tools.javac.util.List");
        Class<?> nameCls = Class.forName("com.sun.tools.javac.util.Name");
        Class<?> jcClassDecl = Class.forName("com.sun.tools.javac.tree.JCTree$JCClassDecl");
        Class<?> jcExpr = Class.forName("com.sun.tools.javac.tree.JCTree$JCExpression");
        Class<?> jcTree = Class.forName("com.sun.tools.javac.tree.JCTree");
        Class<?> jcMethodDecl = Class.forName("com.sun.tools.javac.tree.JCTree$JCMethodDecl");
        Class<?> jcVarDecl = Class.forName("com.sun.tools.javac.tree.JCTree$JCVariableDecl");
        Class<?> jcTypeParam = Class.forName("com.sun.tools.javac.tree.JCTree$JCTypeParameter");
        Class<?> jcStmt = Class.forName("com.sun.tools.javac.tree.JCTree$JCStatement");
        Class<?> flagsCls = Class.forName("com.sun.tools.javac.code.Flags");

        Method fromString = names.getClass().getMethod("fromString", String.class);
        Method makerModifiers = tmCls.getMethod("Modifiers", long.class);
        long PUBLIC = flagsCls.getField("PUBLIC").getLong(null);
        long STATIC = flagsCls.getField("STATIC").getLong(null);
        long FINAL = flagsCls.getField("FINAL").getLong(null);
        Object mods = makerModifiers.invoke(treeMaker, PUBLIC | STATIC | FINAL);
        Object name = fromString.invoke(names, innerName);

        Method nil = listCls.getMethod("nil");
        Object nilList = nil.invoke(null);
        Method classDef = tmCls.getMethod("ClassDef", modsCls, nameCls, listCls,
                Class.forName("com.sun.tools.javac.tree.JCTree$JCExpression"), listCls, listCls);
        Object meta = classDef.invoke(treeMaker, mods, name, nilList, null, nilList, nilList);

        Field defsFieldOwner = owner.getClass().getField("defs");
        Object defs = defsFieldOwner.get(owner);
        Method append = listCls.getMethod("append", Object.class);
        Object newDefs = append.invoke(defs, meta);
        defsFieldOwner.set(owner, newDefs);

        Object privateMods = makerModifiers.invoke(treeMaker, flagsCls.getField("PRIVATE").getLong(null));
        Method block = tmCls.getMethod("Block", long.class, listCls);
        Method of = listCls.getMethod("of", Object.class);
        Object emptyStmts = of.invoke(null, (Object) null);
        Object ctorBody = block.invoke(treeMaker, 0L, nilList);
        Method methodDef = tmCls.getMethod("MethodDef", modsCls, nameCls,
                Class.forName("com.sun.tools.javac.tree.JCTree$JCExpression"), listCls, listCls, listCls,
                Class.forName("com.sun.tools.javac.tree.JCTree$JCBlock"),
                Class.forName("com.sun.tools.javac.tree.JCTree$JCExpression"));
        Object ctor = methodDef.invoke(treeMaker, privateMods, fromString.invoke(names, "<init>"), null, nilList,
                nilList, nilList, ctorBody, null);

        Method ident = tmCls.getMethod("Ident", nameCls);
        Method varDef = tmCls.getMethod("VarDef", modsCls, nameCls, jcExpr, jcExpr);
        Object stringType = ident.invoke(treeMaker, fromString.invoke(names, "String"));
        Method literal = tmCls.getMethod("Literal", Object.class);
        Object initSimple = literal.invoke(treeMaker, simpleBinary);
        Object initFull = literal.invoke(treeMaker, fullBinary);
        Object fSimple = varDef.invoke(treeMaker, makerModifiers.invoke(treeMaker, PUBLIC | STATIC | FINAL),
                fromString.invoke(names, "SIMPLE_CLASS_NAME"), stringType, initSimple);
        Object fFull = varDef.invoke(treeMaker, makerModifiers.invoke(treeMaker, PUBLIC | STATIC | FINAL),
                fromString.invoke(names, "CLASS_NAME"), stringType, initFull);

        Field metaDefs = jcClassDecl.getField("defs");
        Object metaDefs1 = nil.invoke(null);
        metaDefs1 = append.invoke(metaDefs1, ctor);
        metaDefs1 = append.invoke(metaDefs1, fSimple);
        metaDefs1 = append.invoke(metaDefs1, fFull);
        metaDefs.set(meta, metaDefs1);
    }

    private boolean isStaticAllowed(Object decl) throws Exception {
        Class<?> jcClassDecl = Class.forName("com.sun.tools.javac.tree.JCTree$JCClassDecl");
        Field symField = jcClassDecl.getField("sym");
        Object sym = symField.get(decl);
        if (sym == null)
            return true;
        Object owner = sym.getClass().getField("owner").get(sym);
        if (owner != null && owner.getClass().getSimpleName().contains("PackageSymbol"))
            return true;
        long flags = (long) sym.getClass().getMethod("flags").invoke(sym);
        long STATIC = Class.forName("com.sun.tools.javac.code.Flags").getField("STATIC").getLong(null);
        return (flags & STATIC) != 0;
    }

    private void removeAnnotation(Object decl) throws Exception {
        Class<?> modsCls = Class.forName("com.sun.tools.javac.tree.JCTree$JCModifiers");
        Class<?> listCls = Class.forName("com.sun.tools.javac.util.List");
        Field modsField = decl.getClass().getField("mods");
        Object mods = modsField.get(decl);
        if (mods == null)
            return;
        Field annsField = modsCls.getField("annotations");
        Object anns = annsField.get(mods);
        if (anns == null)
            return;
        Method nil = listCls.getMethod("nil");
        Object filtered = nil.invoke(null);
        Method toString = Class.forName("com.sun.tools.javac.tree.JCTree$JCAnnotation").getMethod("toString");
        Method append = listCls.getMethod("append", Object.class);
        for (Object a : (Iterable<?>) anns) {
            String at = String.valueOf(toString.invoke(a));
            if (at.endsWith("ClassMetaConstants") || at.equals(ClassMetaConstants.class.getName()))
                continue;
            filtered = append.invoke(filtered, a);
        }
        annsField.set(mods, filtered);
    }

    private String stripPackage(String full) {
        int idx = full.lastIndexOf('.') + 1;
        return idx > 0 ? full.substring(idx) : full;
    }

    private void addOpensForProcessor() throws Exception {
        Class<?> moduleCls = Class.forName("java.lang.Module");
        Object boot = Class.forName("java.lang.ModuleLayer").getMethod("boot").invoke(null);
        Object opt = boot.getClass().getMethod("findModule", String.class).invoke(boot, "jdk.compiler");
        Object jdkCompiler = opt.getClass().getMethod("get").invoke(opt);
        Object ownModule = this.getClass().getModule();
        Method implAddOpens = moduleCls.getDeclaredMethod("implAddOpens", String.class, moduleCls);
        forceAccessible(implAddOpens);
        String[] pkgs = new String[] {
                "com.sun.tools.javac.api",
                "com.sun.tools.javac.processing",
                "com.sun.tools.javac.tree",
                "com.sun.tools.javac.util",
                "com.sun.tools.javac.code",
                "com.sun.tools.javac.comp",
                "com.sun.tools.javac.parser",
                "com.sun.tools.javac.main",
                "com.sun.tools.javac.model",
                "com.sun.tools.javac.file",
                "com.sun.tools.javac.jvm"
        };
        for (String p : pkgs)
            implAddOpens.invoke(jdkCompiler, p, ownModule);
        disableIllegalAccessLogger();
    }

    private void forceAccessible(Method m) throws Exception {
        try {
            m.setAccessible(true);
        } catch (Throwable ignored) {
            Field f = Class.forName("jdk.internal.reflect.ReflectionFactory")
                    .getDeclaredField("directMethodHandleAccess");
            f.setAccessible(true);
            m.setAccessible(true);
        }
    }

    private void disableIllegalAccessLogger() {
        try {
            Class<?> logger = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field f = logger.getDeclaredField("logger");
            f.setAccessible(true);
            f.set(null, null);
        } catch (Throwable ignored) {
        }
    }
}