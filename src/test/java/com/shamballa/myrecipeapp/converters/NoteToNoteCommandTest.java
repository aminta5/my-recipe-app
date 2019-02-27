package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.NoteCommand;
import com.shamballa.myrecipeapp.model.Note;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoteToNoteCommandTest {

    private static final Long LONG_VALUE = 1L;
    private final static String NOTES_VALUE = "test notes";

    NoteToNoteCommand noteToNoteCommand;

    @Before
    public void setUp() throws Exception {
        noteToNoteCommand = new NoteToNoteCommand();
    }

    @Test
    public void testNullObject(){
        assertNull(noteToNoteCommand.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(new Note());
    }

    @Test
    public void convert() {
        Note notes = new Note();
        notes.setId(LONG_VALUE);
        notes.setNotes(NOTES_VALUE);

        NoteCommand noteCommand = noteToNoteCommand.convert(notes);

        assertNotNull(noteCommand);
        assertEquals(LONG_VALUE, noteCommand.getId());
    }
}