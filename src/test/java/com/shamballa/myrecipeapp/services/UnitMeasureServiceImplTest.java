package com.shamballa.myrecipeapp.services;

import com.shamballa.myrecipeapp.commands.UnitMeasureCommand;
import com.shamballa.myrecipeapp.converters.UnitMeasureToUnitMeasureCommand;
import com.shamballa.myrecipeapp.model.UnitMeasure;
import com.shamballa.myrecipeapp.repositories.UnitMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UnitMeasureServiceImplTest {
    UnitMeasureToUnitMeasureCommand unitMeasureToUnitMeasureCommand = new UnitMeasureToUnitMeasureCommand();
    UnitMeasureService unitMeasureService;

    @Mock
    UnitMeasureRepository unitMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitMeasureService = new UnitMeasureServiceImpl(unitMeasureRepository, unitMeasureToUnitMeasureCommand);

    }

    @Test
    public void findAllUnitMeasures() throws Exception {
        //given
        Set<UnitMeasure> unitMeasureSet = new HashSet<>();
        UnitMeasure uom1 = new UnitMeasure();
        uom1.setId(1L);
        unitMeasureSet.add(uom1);

        UnitMeasure uom2 = new UnitMeasure();
        uom2.setId(2L);
        unitMeasureSet.add(uom2);

        when(unitMeasureRepository.findAll()).thenReturn(unitMeasureSet);

        //when
        Set<UnitMeasureCommand> commands = unitMeasureService.findAllUnitMeasures();

        //then
        assertEquals(2, commands.size());
        verify(unitMeasureRepository, times(1)).findAll();
    }
}