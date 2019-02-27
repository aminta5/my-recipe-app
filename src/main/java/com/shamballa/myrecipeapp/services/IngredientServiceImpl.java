package com.shamballa.myrecipeapp.services;

import com.shamballa.myrecipeapp.commands.IngredientCommand;
import com.shamballa.myrecipeapp.converters.IngredientCommandToIngredient;
import com.shamballa.myrecipeapp.converters.IngredientToIngredientCommand;
import com.shamballa.myrecipeapp.model.Ingredient;
import com.shamballa.myrecipeapp.model.Recipe;
import com.shamballa.myrecipeapp.repositories.RecipeRepository;
import com.shamballa.myrecipeapp.repositories.UnitMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Optional;
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitMeasureRepository unitMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, UnitMeasureRepository unitMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitMeasureRepository = unitMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            log.error("Recipe is not found id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(id)).map(ingredient ->  ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            log.error("Ingredient id is not found " + id);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        System.out.println(ingredientCommand.getId() + " COMMAND ID");
        if(!recipeOptional.isPresent()){
            log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }
        else{
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUnitMeasure(unitMeasureRepository.findById(ingredientCommand.getUnitMeasure().getId()).orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
            }
            else{
                //if it is new ingredient then add the new ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
                System.out.println(ingredient.getId() + " INGREDIENT IN SEVICE IMPL");
            }

            Recipe savedRecipe = recipeRepository.save(recipe);
            savedRecipe.getIngredients().stream().forEach(ingredient -> System.out.println(ingredient.getId()));
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitMeasure().getId().equals(ingredientCommand.getUnitMeasure().getId()))
                        .findFirst();
            }
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());

        }
    }

    @Override
    public void deleteIngredientById(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        Ingredient ingredient = recipe.getIngredients().stream().filter(ingredient1 -> ingredient1.getId().equals(ingredientId)).findFirst().get();
        ingredient.setRecipe(null);
        recipe.getIngredients().remove(ingredient);
        recipeRepository.save(recipe);


    }

}
