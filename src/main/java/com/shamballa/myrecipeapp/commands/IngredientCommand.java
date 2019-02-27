package com.shamballa.myrecipeapp.commands;

import com.shamballa.myrecipeapp.model.Recipe;
import com.shamballa.myrecipeapp.model.UnitMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private UnitMeasureCommand unitMeasure;
    private BigDecimal amount;
    private Long recipeId;

    public String toString(){
        return "id: " + this.id + "\n" + "description: " + this.description + "\n" + "unit of measure: " + unitMeasure.getId();

    }

}
