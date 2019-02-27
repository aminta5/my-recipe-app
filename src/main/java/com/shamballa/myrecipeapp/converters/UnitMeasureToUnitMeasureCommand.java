package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.UnitMeasureCommand;
import com.shamballa.myrecipeapp.model.UnitMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitMeasureToUnitMeasureCommand implements Converter<UnitMeasure, UnitMeasureCommand> {
    @Synchronized
    @Nullable

    @Override
    public UnitMeasureCommand convert(UnitMeasure source) {
        if(source == null){
            return null;
        }
        final UnitMeasureCommand uomCommand = new UnitMeasureCommand();
        uomCommand.setId(source.getId());
        uomCommand.setMeasure(source.getMeasure());
        return uomCommand;
    }
}
