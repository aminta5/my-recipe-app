package com.shamballa.myrecipeapp.controllers;

import com.shamballa.myrecipeapp.commands.RecipeCommand;
import com.shamballa.myrecipeapp.services.ImageService;
import com.shamballa.myrecipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController imageController;
    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    public void showUploadFormTest()throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/1/image")).andExpect(status().isOk()).andExpect(model().attributeExists("recipe"));
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void handleImagePost()throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    public void renderImageFromDB() throws Exception{
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        String s = "fake image text";
        //create a Byte array boxed
        Byte[] boxedArray = new Byte[s.getBytes().length];

        int index = 0;
        for(byte b : s.getBytes()){
            boxedArray[index++] = b; //getBytes() returns primitive array byte[]; we box it here in this line
        }

        command.setImage(boxedArray);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        //then
       MockHttpServletResponse response =  mockMvc.perform(get("/recipe/1/recipeimage"))
               .andExpect(status().isOk())
               .andReturn().getResponse();

       byte[] responseBytes = response.getContentAsByteArray();

       assertEquals(s.getBytes().length, responseBytes.length);
    }
}