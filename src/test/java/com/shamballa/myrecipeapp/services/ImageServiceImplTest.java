package com.shamballa.myrecipeapp.services;

import com.shamballa.myrecipeapp.model.Recipe;
import com.shamballa.myrecipeapp.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFileTest() throws Exception{
        //given
        Long id = 1L;
        MultipartFile multipatFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Recipe App".getBytes());
        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        service.saveImageFile(id,multipatFile);

        //then
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipatFile.getBytes().length, savedRecipe.getImage().length);


    }
}