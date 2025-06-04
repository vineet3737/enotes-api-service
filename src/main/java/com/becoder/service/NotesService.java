package com.becoder.service;

import com.becoder.dto.NotesDto;
import com.becoder.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface NotesService {

    public Boolean saveNotes(String notes, MultipartFile file) throws Exception;

    public List<NotesDto> getAllNotes();

    byte[] downloadFile(FileDetails fileDetails) throws IOException;

    FileDetails getFileDetails(Integer id);
}
