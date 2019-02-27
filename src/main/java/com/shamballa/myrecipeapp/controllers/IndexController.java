package com.shamballa.myrecipeapp.controllers;

import com.shamballa.myrecipeapp.model.Category;
import com.shamballa.myrecipeapp.model.UnitMeasure;
import com.shamballa.myrecipeapp.repositories.CategoryRepository;
import com.shamballa.myrecipeapp.repositories.RecipeRepository;
import com.shamballa.myrecipeapp.repositories.UnitMeasureRepository;
import com.shamballa.myrecipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
@Slf4j
@Controller
public class IndexController {

    //private final CategoryRepository categoryRepository;
    //private final UnitMeasureRepository unitMeasureRepository;

    private final RecipeService recipeService;

    //constructor


    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping({"", "/", "/index"})
    public String getIndex(Model model){

        log.debug("Getting Index page");

        model.addAttribute("recipes", recipeService.getRecipes());
        //System.out.println("Cat id is " + optionalCategory.get().getId());
        //System.out.println("Measure id is: " + optionalMeasure.get().getId());

        return "index";
    }
}
