package com.becoder.controller;

import com.becoder.dto.NotesDto;
import com.becoder.entity.Notes;
import com.becoder.service.NotesService;
import com.becoder.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/saveNotes")
    public ResponseEntity<?> saveNotes(@RequestParam String notes,
                                       @RequestParam (required = false) MultipartFile file) throws Exception {
        Boolean saveNotes = notesService.saveNotes(notes, file);
        if(saveNotes){
            return CommonUtils.createBuildResponseMessage("Notes saved successfully", HttpStatus.CREATED);
        }
        return CommonUtils.createErrorResponseMessage("Notes not saved !!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> allNotes = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtils.createBuildResponse(allNotes, HttpStatus.OK);
    }
}
