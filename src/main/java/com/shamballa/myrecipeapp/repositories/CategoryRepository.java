package com.shamballa.myrecipeapp.repositories;

import com.shamballa.myrecipeapp.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String Description);
}
