package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.IngredientCommand;
import com.shamballa.myrecipeapp.model.Ingredient;
import com.shamballa.myrecipeapp.model.UnitMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitMeasureToUnitMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitMeasureToUnitMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable

    @Override
    public IngredientCommand convert(Ingredient source) {

        if(source == null){
            return null;
        }

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(source.getId());

        if(source.getRecipe() != null){
            ingredientCommand.setRecipeId(source.getRecipe().getId());
        }

        ingredientCommand.setAmount(source.getAmount());
        ingredientCommand.setDescription(source.getDescription());
        ingredientCommand.setUnitMeasure(uomConverter.convert(source.getUnitMeasure()));

        return ingredientCommand;
    }
}
