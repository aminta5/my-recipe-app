package com.shamballa.myrecipeapp.repositories;

import com.shamballa.myrecipeapp.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
