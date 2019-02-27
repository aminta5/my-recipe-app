package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.CategoryCommand;
import com.shamballa.myrecipeapp.model.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {
    private static final Long ID_VALUE = 1L;
    private static final String DESCRIPTION = "description";

    CategoryToCategoryCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void convert() {
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        CategoryCommand categoryCommand = converter.convert(category);

        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new Category()));
    }
}