package com.shamballa.myrecipeapp.services;

import com.shamballa.myrecipeapp.commands.UnitMeasureCommand;
import com.shamballa.myrecipeapp.converters.UnitMeasureToUnitMeasureCommand;
import com.shamballa.myrecipeapp.repositories.UnitMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitMeasureServiceImpl implements UnitMeasureService {

    private final UnitMeasureRepository unitMeasureRepository;
    private final UnitMeasureToUnitMeasureCommand uomToUomCommand;

    public UnitMeasureServiceImpl(UnitMeasureRepository unitMeasureRepository,
                                  UnitMeasureToUnitMeasureCommand uomToUomCommand) {
        this.unitMeasureRepository = unitMeasureRepository;
        this.uomToUomCommand = uomToUomCommand;
    }

    @Override
    public Set<UnitMeasureCommand> findAllUnitMeasures() {
        return StreamSupport.stream(unitMeasureRepository.findAll().spliterator(), false).map(uomToUomCommand :: convert).collect(Collectors.toSet());
    }
}
