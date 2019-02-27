package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.UnitMeasureCommand;
import com.shamballa.myrecipeapp.model.UnitMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitMeasureCommandToUnitMeasure implements Converter<UnitMeasureCommand, UnitMeasure> {

    @Synchronized
    @Nullable

    @Override
    public UnitMeasure convert(UnitMeasureCommand source) {
        if (source == null){
            return null;
        }
        final UnitMeasure uom = new UnitMeasure();
        uom.setId(source.getId());
        uom.setMeasure(source.getMeasure());

        return uom;
    }
}
