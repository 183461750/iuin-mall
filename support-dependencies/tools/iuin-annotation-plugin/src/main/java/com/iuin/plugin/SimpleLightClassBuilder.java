package com.iuin.plugin;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.impl.light.LightModifierList;
import com.intellij.psi.impl.light.LightPsiClassBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SimpleLightClassBuilder extends LightPsiClassBuilder {
    private final List<PsiField> myFields = new ArrayList<>();

    public SimpleLightClassBuilder(PsiElement context, String name) {
        super(context, name);
    }

    public void addField(PsiField field) {
        myFields.add(field);
    }

    public void setModifiers(String... modifiers) {
        for (String modifier : modifiers) {
            ((LightModifierList) getModifierList()).addModifier(modifier);
        }
    }

    @Override
    public @NotNull PsiField[] getFields() {
        return myFields.toArray(PsiField.EMPTY_ARRAY);
    }
}
