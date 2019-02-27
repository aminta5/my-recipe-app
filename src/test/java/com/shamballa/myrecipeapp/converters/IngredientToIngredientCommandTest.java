package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.IngredientCommand;
import com.shamballa.myrecipeapp.model.Ingredient;
import com.shamballa.myrecipeapp.model.Recipe;
import com.shamballa.myrecipeapp.model.UnitMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {
    IngredientToIngredientCommand converter;
    private static final Long LONG_VALUE = 1L;
    private static final Long UM_ID_VALUE = 2L;
    private static final Recipe RECIPE = new Recipe();
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final String DESCRIPTION = "Cheeseburger";


    @Before
    public void setUp() throws Exception {
        converter = new IngredientToIngredientCommand(new UnitMeasureToUnitMeasureCommand());
    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    public void converNullUM(){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(LONG_VALUE);
        ingredient.setRecipe(RECIPE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setUnitMeasure(null);

        IngredientCommand ingredientCommand = converter.convert(ingredient);
        assertNull(ingredientCommand.getUnitMeasure());
        assertEquals(LONG_VALUE, ingredientCommand.getId());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
    }

    @Test
    public void convertWithUM() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(LONG_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setRecipe(RECIPE);
        ingredient.setDescription(DESCRIPTION);

        UnitMeasure unitMeasure = new UnitMeasure();
        unitMeasure.setId(UM_ID_VALUE);
        ingredient.setUnitMeasure(unitMeasure);

        IngredientCommand ingredientCommand = converter.convert(ingredient);

        assertEquals(LONG_VALUE, ingredientCommand.getId());
        assertNotNull(ingredientCommand.getUnitMeasure());
        assertEquals(UM_ID_VALUE, ingredientCommand.getUnitMeasure().getId());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        //assertEquals(RECIPE, ingredientCommand.getRecipeId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
    }
}