package com.becoder.serviceImpl;

import com.becoder.dto.NotesDto;
import com.becoder.entity.Notes;
import com.becoder.repository.NotesRepos;
import com.becoder.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepos notesRepos;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveNotes(NotesDto notesDto) {
        //Validation

        Notes notes = mapper.map(notesDto, Notes.class);

        Notes saveNotes = notesRepos.save(notes);
        if(!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }
        return false;
    }

    @Override
    public List<NotesDto> getAllNotes() {
        List<Notes> allNotes = notesRepos.findAll();
        List<NotesDto> notesDtos = allNotes.stream()
                .map(note -> mapper.map(note, NotesDto.class)).toList();
        return notesDtos;
    }
}
