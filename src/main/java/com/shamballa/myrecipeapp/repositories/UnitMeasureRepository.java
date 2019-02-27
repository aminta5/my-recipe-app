package com.shamballa.myrecipeapp.repositories;

import com.shamballa.myrecipeapp.model.UnitMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitMeasureRepository extends CrudRepository<UnitMeasure, Long> {
    Optional<UnitMeasure> findByMeasure(String measure);
}
