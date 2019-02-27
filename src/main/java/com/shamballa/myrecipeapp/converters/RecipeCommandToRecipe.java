package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.RecipeCommand;
import com.shamballa.myrecipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NoteCommandToNote noteCommandToNote;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory,
                                 IngredientCommandToIngredient ingredientCommandToIngredient, NoteCommandToNote noteCommandToNote) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.noteCommandToNote = noteCommandToNote;
    }

    @Synchronized
    @Nullable

    @Override
    public Recipe convert(RecipeCommand source) {
        if(source == null)
        {
            return null;
        }
        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(noteCommandToNote.convert(source.getNotes()));

        if(source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories().forEach(category -> recipe.getCategories().add(categoryCommandToCategory.convert(category)));
        }

        if(source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients().forEach(ingredient -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredient)));
        }

        return recipe;
    }
}
