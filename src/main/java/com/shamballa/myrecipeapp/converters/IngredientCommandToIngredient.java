package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.IngredientCommand;
import com.shamballa.myrecipeapp.commands.UnitMeasureCommand;
import com.shamballa.myrecipeapp.model.Ingredient;
import com.shamballa.myrecipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitMeasureCommandToUnitMeasure uomConverter;

    public IngredientCommandToIngredient(UnitMeasureCommandToUnitMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Synchronized

    @Override
    public Ingredient convert(IngredientCommand source) {
        if(source == null){
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());

        if(source.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUnitMeasure(uomConverter.convert(source.getUnitMeasure()));
        return ingredient;
    }
}
