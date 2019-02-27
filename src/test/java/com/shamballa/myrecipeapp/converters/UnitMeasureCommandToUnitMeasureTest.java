package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.UnitMeasureCommand;
import com.shamballa.myrecipeapp.model.UnitMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitMeasureCommandToUnitMeasureTest {

    private final Long id = 1L;
    private final String measure = "measure";

    UnitMeasureCommandToUnitMeasure unitMeasureCommandToUnitMeasure;

    @Before
    public void setUp() throws Exception {
        unitMeasureCommandToUnitMeasure = new UnitMeasureCommandToUnitMeasure();
    }

    @Test
    public void testOfNullable(){
       assertNull(null);
    }

    @Test
    public void testEmptyObj(){
        assertNotNull( unitMeasureCommandToUnitMeasure.convert(new UnitMeasureCommand()));
    }

    @Test
    public void convert() {

        UnitMeasureCommand umc = new UnitMeasureCommand();
        umc.setId(id);
        umc.setMeasure(measure);

        UnitMeasure unitMeasure = unitMeasureCommandToUnitMeasure.convert(umc);

        assertNotNull(unitMeasure);
        assertEquals(unitMeasure.getId(), id);
        assertEquals(unitMeasure.getMeasure(), measure);
    }
}