package com.shamballa.myrecipeapp.services;

import com.shamballa.myrecipeapp.commands.IngredientCommand;
import com.shamballa.myrecipeapp.model.Ingredient;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteIngredientById(Long recipeId, Long ingredientId);
}
