package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.RecipeCommand;
import com.shamballa.myrecipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final NoteToNoteCommand noteToNoteCommand;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryToCategoryCommand,
                                 IngredientToIngredientCommand ingredientToIngredientCommand, NoteToNoteCommand noteToNoteCommand) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.noteToNoteCommand = noteToNoteCommand;
    }

    @Synchronized
    @Nullable

    @Override
    public RecipeCommand convert(Recipe source) {
        if(source == null){
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setImage(source.getImage());
        recipeCommand.setNotes(noteToNoteCommand.convert(source.getNotes()));

        if(source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories().forEach(category -> recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category)));
        }

        if(source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients().forEach(ingredient -> recipeCommand.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
        }

        return recipeCommand;
    }
}
