package com.becoder.service;

import com.becoder.dto.FavouriteNoteDto;
import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.entity.FavouriteNote;
import com.becoder.entity.FileDetails;
import com.becoder.entity.Notes;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface NotesService {

    public Boolean saveNotes(String notes, MultipartFile file) throws Exception;

    public List<NotesDto> getAllNotes();

    byte[] downloadFile(FileDetails fileDetails) throws IOException;

    FileDetails getFileDetails(Integer id);

    NotesResponse getAllNotesByUser(int pageNo, int pageSize);

    void deleteNotes(Integer id);

    void restoreNotes(Integer id);

    List<NotesDto> getUserRecycleBinNotes();

    void hardDeleteNotes(Integer id);

    void emptyRecycleBin();

    public void favNotes(Integer noteId);

    public void unfavNote(Integer favouriteId);

    List<FavouriteNoteDto> getUserFavouriteNotes();


    Boolean copyNotes(int id);
}
