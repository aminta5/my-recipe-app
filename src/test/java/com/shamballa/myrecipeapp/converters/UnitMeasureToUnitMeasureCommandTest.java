package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.UnitMeasureCommand;
import com.shamballa.myrecipeapp.model.UnitMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitMeasureToUnitMeasureCommandTest {
    private static final Long LONG_VALUE = 1L;
    private static final  String MEASURE = "measure";


    UnitMeasureToUnitMeasureCommand unitMeasureToUnitMeasureCommand;

    @Before
    public void setUp() throws Exception {
        unitMeasureToUnitMeasureCommand = new UnitMeasureToUnitMeasureCommand();
    }

    @Test
    public void testNullObject(){
        assertNull(unitMeasureToUnitMeasureCommand.convert(null));
    }

    @Test
    public void testEmptyObject(){
        unitMeasureToUnitMeasureCommand.convert(new UnitMeasure());
    }

    @Test
    public void convert() {
        UnitMeasure unitMeasure = new UnitMeasure();
        unitMeasure.setId(LONG_VALUE);
        unitMeasure.setMeasure(MEASURE);

        UnitMeasureCommand umc = unitMeasureToUnitMeasureCommand.convert(unitMeasure);

        assertNotNull(umc);
        assertEquals(umc.getId(), LONG_VALUE);
        assertEquals(umc.getMeasure(), MEASURE);
    }
}