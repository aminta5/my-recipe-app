package com.shamballa.myrecipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnitMeasureCommand {
    private Long id;
    private String measure;
}
