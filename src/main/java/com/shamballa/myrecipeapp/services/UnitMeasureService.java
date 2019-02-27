package com.shamballa.myrecipeapp.services;

import com.shamballa.myrecipeapp.commands.UnitMeasureCommand;

import java.util.Set;

public interface UnitMeasureService {
    Set<UnitMeasureCommand> findAllUnitMeasures();
}
