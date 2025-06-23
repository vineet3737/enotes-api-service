package com.becoder.controller;

import com.becoder.dto.FavouriteNoteDto;
import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.entity.FavouriteNote;
import com.becoder.entity.FileDetails;
import com.becoder.entity.Notes;
import com.becoder.service.NotesService;
import com.becoder.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveNotes(@RequestParam String notes,
                                       @RequestParam (required = false) MultipartFile file) throws Exception {
        Boolean saveNotes = notesService.saveNotes(notes, file);
        if(saveNotes){
            return CommonUtils.createBuildResponseMessage("Notes saved successfully", HttpStatus.CREATED);
        }
        return CommonUtils.createErrorResponseMessage("Notes not saved !!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> allNotes = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtils.createBuildResponse(allNotes, HttpStatus.OK);
    }

    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        int userId = 1;
        NotesResponse allNotesByUser = notesService.getAllNotesByUser(userId, pageNo, pageSize);
        if(ObjectUtils.isEmpty(allNotesByUser)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtils.createBuildResponse(allNotesByUser, HttpStatus.OK);
    }
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id){
            notesService.deleteNotes(id);
        return CommonUtils.createBuildResponseMessage("Notes deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id){
        notesService.restoreNotes(id);
        return CommonUtils.createBuildResponseMessage("Notes restored successfully", HttpStatus.OK);
    }

    @GetMapping("/recycleBin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserRecycleBinNotes(){
        int userId = 1;
        List<NotesDto> notesDtos = notesService.getUserRecycleBinNotes(userId);
        if(CollectionUtils.isEmpty(notesDtos)){
            return CommonUtils.createBuildResponseMessage("Recycle bin is empty", HttpStatus.OK);
        }
        return CommonUtils.createBuildResponse(notesDtos, HttpStatus.OK);
    }

    @DeleteMapping("/hardDelete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id){
        notesService.hardDeleteNotes(id);
        return CommonUtils.createBuildResponseMessage("Notes deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/emptyRecycle")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> emptyRecycleBin(){
        int userId = 1;
        notesService.emptyRecycleBin(userId);
        return CommonUtils.createBuildResponseMessage("All Notes deleted successfully", HttpStatus.OK);
    }


    @GetMapping("/fav/{noteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getFavNotes(@PathVariable Integer noteId){
        notesService.favNotes(noteId);
        return CommonUtils.createBuildResponseMessage("Favourite Notes added!!", HttpStatus.CREATED);
    }

    @DeleteMapping("/unfav/{favId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUnFavNotes(@PathVariable Integer favId){
        notesService.unfavNote(favId);
        return CommonUtils.createBuildResponseMessage("Favourite Notes removed!!", HttpStatus.OK);
    }

    @GetMapping("favNotesByUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserFavouriteNotes(){
        List<FavouriteNoteDto> userFavouriteNotes = notesService.getUserFavouriteNotes();
        if(CollectionUtils.isEmpty(userFavouriteNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtils.createBuildResponse(userFavouriteNotes, HttpStatus.OK);
    }
    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> copyNotes(@PathVariable int id){
        Boolean copyingNotes =  notesService.copyNotes(id);
        if(copyingNotes){
            return CommonUtils.createBuildResponseMessage("Notes Copies successfully!!", HttpStatus.OK);
        }
        return CommonUtils.createErrorResponseMessage("Notes not copied !!", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
