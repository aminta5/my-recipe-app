package com.shamballa.myrecipeapp.services;

import com.shamballa.myrecipeapp.commands.RecipeCommand;
import com.shamballa.myrecipeapp.converters.RecipeCommandToRecipe;
import com.shamballa.myrecipeapp.converters.RecipeToRecipeCommand;
import com.shamballa.myrecipeapp.exceptions.NotFoundException;
import com.shamballa.myrecipeapp.model.Recipe;
import com.shamballa.myrecipeapp.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }


    @Test
    public void findByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        Recipe foundRecipe  = recipeService.findById(1L);
        assertNotNull("Recipe not found", foundRecipe);
        assertEquals(foundRecipe.getId(), recipeOptional.get().getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }




    @Test
    public void getRecipesTest() throws Exception {
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipeData);
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdTestNotFound() {
        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //testing line
        Recipe returnedRecipe = recipeService.findById(1L);
    }

    @Test
    public void testDeleteById(){
        Long idToDelete = Long.valueOf(2L);
        recipeService.deleteById(idToDelete);

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void findCommandById() {
        Recipe recipe = new Recipe();
        recipe.setId((1L));
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand returnedCommand = recipeService.findCommandById(1L);

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }
}