package com.shamballa.myrecipeapp.controllers;

import com.shamballa.myrecipeapp.commands.RecipeCommand;
import com.shamballa.myrecipeapp.exceptions.NotFoundException;
import com.shamballa.myrecipeapp.model.Recipe;
import com.shamballa.myrecipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class RecipeControllerTest {

    @Mock
    RecipeService service;

    RecipeController recipeController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void showRecipe() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(service.findById(anyLong())).thenReturn(recipe);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));

    }

    @Test
    public void testGetRecipeNotFound() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(service.findById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postNewRecipeForm() throws Exception{
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(service.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(view().name("redirect:/recipe/2/show"));



    }

    @Test
    public void testGetUpdateView() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        when(service.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeForm"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testDeleteAction() throws Exception{
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(service, times(1)).deleteById(anyLong());
    }
}