package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.CategoryCommand;
import com.shamballa.myrecipeapp.model.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {
    private static final Long LONG_ID = 2L;
    private static final String DESCRIPTION = "description";

    CategoryCommandToCategory converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void convert() {
        CategoryCommand categoryCommand= new CategoryCommand();
        categoryCommand.setId(LONG_ID);
        categoryCommand.setDescription(DESCRIPTION);

        Category category = converter.convert(categoryCommand);
        assertNotNull(category);
        assertEquals(LONG_ID, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyOnject(){
        assertNotNull(converter.convert(new CategoryCommand()));
    }
}