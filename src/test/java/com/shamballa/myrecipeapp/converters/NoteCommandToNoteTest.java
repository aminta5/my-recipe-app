package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.NoteCommand;
import com.shamballa.myrecipeapp.model.Note;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoteCommandToNoteTest {
    private static final Long LONG_VALUE = 1L;
    private static final String NOTES_VALUE = "test string";

    NoteCommandToNote converter;

    @Before
    public void setUp() throws Exception {
        converter = new NoteCommandToNote();
    }

    @Test
    public void testNullObject(){
        converter.convert(null);
    }

    @Test
    public void testEmptyObject(){
        converter.convert(new NoteCommand());
    }

    @Test
    public void convert() {
        NoteCommand noteCommand = new NoteCommand();
        noteCommand.setId(LONG_VALUE);
        noteCommand.setNotes(NOTES_VALUE);

        Note notes  = converter.convert(noteCommand);
        assertNotNull(notes);
        assertEquals(LONG_VALUE, notes.getId());
        assertEquals(NOTES_VALUE, notes.getNotes());
    }
}