package com.shamballa.myrecipeapp.repositories;

import com.shamballa.myrecipeapp.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
