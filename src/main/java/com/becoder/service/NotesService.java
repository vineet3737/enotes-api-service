package com.becoder.service;

import com.becoder.dto.NotesDto;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(NotesDto notesDto);

    public List<NotesDto> getAllNotes();
}
