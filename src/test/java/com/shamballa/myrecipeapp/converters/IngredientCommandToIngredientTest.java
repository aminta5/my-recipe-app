package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.IngredientCommand;
import com.shamballa.myrecipeapp.commands.RecipeCommand;
import com.shamballa.myrecipeapp.commands.UnitMeasureCommand;
import com.shamballa.myrecipeapp.model.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {
    private static final Long ID_VALUE = 1L;
    private static final Long ID_UM_VALUE = 2L;
    private static final RecipeCommand recipeCommand= new RecipeCommand();
    private static final String DESCRIPTION = "Cheeseburger";
    private static final BigDecimal AMOUNT = new BigDecimal("2");

    IngredientCommandToIngredient converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientCommandToIngredient( new UnitMeasureCommandToUnitMeasure());
    }

    @Test
    public void convertWithUM() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);

        UnitMeasureCommand unitMeasureCommand = new UnitMeasureCommand();
        unitMeasureCommand.setId(ID_UM_VALUE);
        ingredientCommand.setUnitMeasure(unitMeasureCommand);

        Ingredient ingredient = converter.convert(ingredientCommand);

        assertNotNull(ingredient);
        assertNotNull(ingredient.getUnitMeasure());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(ID_UM_VALUE, ingredient.getUnitMeasure().getId());


    }
    @Test
    public void convertWithNullUM(){
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setUnitMeasure(null);

        Ingredient ingredient = converter.convert(ingredientCommand);

        assertNotNull(ingredient);
        assertNull(ingredient.getUnitMeasure());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());

    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new IngredientCommand()));
    }
}