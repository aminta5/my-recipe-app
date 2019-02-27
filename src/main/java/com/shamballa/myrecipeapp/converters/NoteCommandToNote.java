package com.shamballa.myrecipeapp.converters;

import com.shamballa.myrecipeapp.commands.NoteCommand;
import com.shamballa.myrecipeapp.model.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NoteCommandToNote implements Converter<NoteCommand, Note> {

    @Synchronized
    @Nullable

    @Override
    public Note convert(NoteCommand source) {
        if(source == null){
            return null;
        }

        final Note notes = new Note();
        notes.setId(source.getId());
        notes.setNotes(source.getNotes());
        return notes;
    }
}
