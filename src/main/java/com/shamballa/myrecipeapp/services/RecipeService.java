package com.shamballa.myrecipeapp.services;

import com.shamballa.myrecipeapp.commands.RecipeCommand;
import com.shamballa.myrecipeapp.model.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand findCommandById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
    void deleteById(Long id);
}
