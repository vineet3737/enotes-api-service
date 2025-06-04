package com.becoder.controller;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.entity.FileDetails;
import com.becoder.entity.Notes;
import com.becoder.service.NotesService;
import com.becoder.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
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
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {
                FileDetails fileDetails =  notesService.getFileDetails(id);
               byte[] data =    notesService.downloadFile(fileDetails);
               HttpHeaders headers = new HttpHeaders();
               String contentType = CommonUtils.getContentType(fileDetails.getOriginalFileName());
               headers.setContentType(MediaType.parseMediaType(contentType));
               headers.setContentDispositionFormData("attachments", fileDetails.getOriginalFileName());
               return ResponseEntity.ok().headers(headers).body(data);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> allNotes = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtils.createBuildResponse(allNotes, HttpStatus.OK);
    }

    @GetMapping("user-notes")
    public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        int userId = 1;
        NotesResponse allNotesByUser = notesService.getAllNotesByUser(userId, pageNo, pageSize);
        if(ObjectUtils.isEmpty(allNotesByUser)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtils.createBuildResponse(allNotesByUser, HttpStatus.OK);
    }
}
