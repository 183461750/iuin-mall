package com.iuin.plugin;

import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.augment.PsiAugmentProvider;
import com.intellij.psi.impl.light.LightFieldBuilder;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MetaAugmentProvider extends PsiAugmentProvider {

    private static final String ANNOTATION_FQN = "com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassMetaConstants";

    @Override
    public @NotNull <Psi extends PsiElement> List<Psi> getAugments(@NotNull PsiElement element,
            @NotNull Class<Psi> type) {
        if (!(element instanceof PsiClass)) {
            return Collections.emptyList();
        }
        PsiClass psiClass = (PsiClass) element;

        // Only process top-level classes or static inner classes
        if (psiClass.getContainingClass() != null && !psiClass.hasModifierProperty(PsiModifier.STATIC)) {
            // Actually the processor supports static nested classes, so this check is fine.
            // But strictly speaking, the annotation might be on any class.
        }

        PsiAnnotation annotation = psiClass.getAnnotation(ANNOTATION_FQN);
        if (annotation == null) {
            return Collections.emptyList();
        }

        if (type == PsiClass.class) {
            return (List<Psi>) Collections.singletonList(createInnerMetaClass(psiClass, annotation));
        }

        return Collections.emptyList();
    }

    private PsiClass createInnerMetaClass(PsiClass containingClass, PsiAnnotation annotation) {
        String innerClassName = getAnnotationValue(annotation, "innerClassName", "Metas");
        if (StringUtil.isEmpty(innerClassName)) {
            innerClassName = "Metas";
        }

        SimpleLightClassBuilder builder = new SimpleLightClassBuilder(containingClass, innerClassName);
        builder.setContainingClass(containingClass);
        builder.setModifiers(PsiModifier.PUBLIC, PsiModifier.STATIC, PsiModifier.FINAL);
        builder.setNavigationElement(annotation);

        // Add fields
        // SIMPLE_CLASS_NAME
        builder.addMethod(JavaPsiFacade.getElementFactory(containingClass.getProject())
                .createMethodFromText("private " + innerClassName + "() {}", containingClass));

        PsiManager manager = containingClass.getManager();
        PsiType stringType = PsiType.getJavaLangString(manager, containingClass.getResolveScope());

        LightFieldBuilder simpleNameField = new LightFieldBuilder(manager, "SIMPLE_CLASS_NAME", stringType);
        simpleNameField.setModifiers(PsiModifier.PUBLIC, PsiModifier.STATIC, PsiModifier.FINAL);
        simpleNameField.setContainingClass(builder);
        simpleNameField.setInitializer(JavaPsiFacade.getElementFactory(containingClass.getProject())
                .createExpressionFromText("\"" + containingClass.getName() + "\"", builder));
        builder.addField(simpleNameField);

        // CLASS_NAME
        LightFieldBuilder fullNameField = new LightFieldBuilder(manager, "CLASS_NAME", stringType);
        fullNameField.setModifiers(PsiModifier.PUBLIC, PsiModifier.STATIC, PsiModifier.FINAL);
        fullNameField.setContainingClass(builder);
        String qualifiedName = containingClass.getQualifiedName();
        if (qualifiedName == null)
            qualifiedName = containingClass.getName();
        fullNameField.setInitializer(JavaPsiFacade.getElementFactory(containingClass.getProject())
                .createExpressionFromText("\"" + qualifiedName + "\"", builder));
        builder.addField(fullNameField);

        return builder;
    }

    private String getAnnotationValue(PsiAnnotation annotation, String attributeName, String defaultValue) {
        PsiAnnotationMemberValue value = annotation.findAttributeValue(attributeName);
        if (value instanceof PsiLiteralExpression) {
            Object val = ((PsiLiteralExpression) value).getValue();
            if (val instanceof String) {
                return (String) val;
            }
        }
        return defaultValue;
    }
}
