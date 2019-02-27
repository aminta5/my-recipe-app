package com.shamballa.myrecipeapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

//@Getter
//@Setter
@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitMeasure unitMeasure;

    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    //constructors

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitMeasure unitMeasure, Recipe recipe) {
        this.description = description;
        this.unitMeasure = unitMeasure;
        this.amount = amount;
        this.recipe = recipe;
    }

    //getters and setters

}
