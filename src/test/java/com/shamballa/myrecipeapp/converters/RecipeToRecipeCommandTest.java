package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.RecipeCommand;
import com.shamballa.myrecipeapp.model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {
    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";

    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;


    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        recipeToRecipeCommand = new RecipeToRecipeCommand(
                new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitMeasureToUnitMeasureCommand()),
                new NoteToNoteCommand());
    }

    @Test
    public void testNullObject() throws  Exception {
        assertNull(recipeToRecipeCommand.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(recipeToRecipeCommand.convert(new Recipe()));
    }

    @Test
    public void convert() {
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        Note notes = new Note();
        notes.setId(NOTES_ID);

        recipe.setNotes(notes);

        Category category = new Category();
        category.setId(CAT_ID_1);

        Category category1 = new Category();
        category1.setId(CAT_ID2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category1);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);

        Ingredient ingredient1= new Ingredient();
        ingredient1.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient1);

        RecipeCommand rc = recipeToRecipeCommand.convert(recipe);

        assertNotNull(rc);
        assertEquals(rc.getId(), RECIPE_ID);
        assertEquals(COOK_TIME, rc.getCookTime());
        assertEquals(PREP_TIME, rc.getPrepTime());
        assertEquals(DESCRIPTION, rc.getDescription());
        assertEquals(DIFFICULTY, rc.getDifficulty());
        assertEquals(DIRECTIONS, rc.getDirections());
        assertEquals(SERVINGS, rc.getServings());
        assertEquals(SOURCE, rc.getSource());
        assertEquals(URL, rc.getUrl());
        assertEquals(NOTES_ID, rc.getNotes().getId());
        assertEquals(2, rc.getCategories().size());
        assertEquals(2, rc.getIngredients().size());

    }
}