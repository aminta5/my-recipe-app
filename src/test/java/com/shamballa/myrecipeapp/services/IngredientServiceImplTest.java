package com.shamballa.myrecipeapp.services;

import com.shamballa.myrecipeapp.commands.IngredientCommand;
import com.shamballa.myrecipeapp.converters.IngredientCommandToIngredient;
import com.shamballa.myrecipeapp.converters.IngredientToIngredientCommand;
import com.shamballa.myrecipeapp.converters.UnitMeasureCommandToUnitMeasure;
import com.shamballa.myrecipeapp.converters.UnitMeasureToUnitMeasureCommand;
import com.shamballa.myrecipeapp.model.Ingredient;
import com.shamballa.myrecipeapp.model.Recipe;
import com.shamballa.myrecipeapp.repositories.RecipeRepository;
import com.shamballa.myrecipeapp.repositories.UnitMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitMeasureRepository unitMeasureRepository;

    IngredientService ingredientService;

    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitMeasureToUnitMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitMeasureCommandToUnitMeasure());

    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(
                recipeRepository,
                ingredientToIngredientCommand,
                unitMeasureRepository,
                ingredientCommandToIngredient);
    }

    @Test
    public void findByRecipeIdAndIngredientId() {
    }

    @Test
    public void findByRecipeIdandRecipeIdHappyPath() throws Exception{
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        assertEquals(Long.valueOf(3), ingredientCommand.getId());
        assertEquals(Long.valueOf(1), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());




    }

    @Test
    public void testSaveIngredientCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(Long.valueOf(3), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDeleteById() throws Exception{
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        //ingredient.setRecipe(recipe);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        ingredientService.deleteIngredientById(1L, 3L);

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}