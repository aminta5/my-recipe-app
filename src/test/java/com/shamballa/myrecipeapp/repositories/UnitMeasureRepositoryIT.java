package com.shamballa.myrecipeapp.repositories;

import com.shamballa.myrecipeapp.model.UnitMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitMeasureRepositoryIT {

    @Autowired
    UnitMeasureRepository unitMeasureRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByMeasure() throws Exception {
        Optional<UnitMeasure> unitMeasure = unitMeasureRepository.findByMeasure("Teaspoon");
        assertEquals("Teaspoon", unitMeasure.get().getMeasure());

    }

    @Test
    public void findByMeasureCup() throws Exception {
        Optional<UnitMeasure> unitMeasure = unitMeasureRepository.findByMeasure("Cup");
        assertEquals("Cup", unitMeasure.get().getMeasure());

    }
}