package com.shamballa.myrecipeapp.controllers;

import com.shamballa.myrecipeapp.commands.IngredientCommand;
import com.shamballa.myrecipeapp.commands.RecipeCommand;
import com.shamballa.myrecipeapp.commands.UnitMeasureCommand;
import com.shamballa.myrecipeapp.services.IngredientService;
import com.shamballa.myrecipeapp.services.RecipeService;
import com.shamballa.myrecipeapp.services.UnitMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitMeasureService unitMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitMeasureService unitMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitMeasureService = unitMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredients list for recipe id:" + recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id,
                                         Model model){
        System.out.println(ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)).getRecipeId() + "<--------INGREDIENT COMMAND");
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        model.addAttribute("uomList", unitMeasureService.findAllUnitMeasures());
        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        //System.out.println(ingredientCommand + " CONTROLLER 1");
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUnitMeasure(new UnitMeasureCommand());
        System.out.println(ingredientCommand + " CONTROLLER 2");
        model.addAttribute("uomList", unitMeasureService.findAllUnitMeasures());
        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping
    @RequestMapping("/recipe/{id}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){
        //System.out.println(ingredientCommand + " AFTER WEB FORM");
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        log.debug("saved receipe id:" + savedIngredientCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedIngredientCommand.getId());
        System.out.println(savedIngredientCommand.getId() + " IDDDDDDDDDD");
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() +"/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id){
        log.debug("deleting ingredient id: " + id);

        ingredientService.deleteIngredientById(Long.valueOf(recipeId), Long.valueOf(id));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
