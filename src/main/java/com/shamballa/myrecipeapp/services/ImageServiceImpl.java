package com.shamballa.myrecipeapp.services;

import com.shamballa.myrecipeapp.model.Recipe;
import com.shamballa.myrecipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try{
            Recipe recipe = recipeRepository.findById(recipeId).get();
            Byte[] imageBytes = new Byte[file.getBytes().length];

            int index = 0;
            for(Byte b : file.getBytes()){
                imageBytes[index++] = b;
            }

            recipe.setImage(imageBytes);
            recipeRepository.save(recipe);

        }catch(IOException e){

            log.error("Error ocurred", e);
            e.printStackTrace();
        }
    }
}
