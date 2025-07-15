package com.becoder.controller;

import com.becoder.dto.FavouriteNoteDto;
import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.endpoint.NotesEndpoint;
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
public class NotesController implements NotesEndpoint {

    @Autowired
    private NotesService notesService;


    @Override
    public ResponseEntity<?> saveNotes(String notes, MultipartFile file) throws Exception {
        Boolean saveNotes = notesService.saveNotes(notes, file);
        if(saveNotes){
            return CommonUtils.createBuildResponseMessage("Notes saved successfully", HttpStatus.CREATED);
        }
        return CommonUtils.createErrorResponseMessage("Notes not saved !!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> downloadFile(Integer id) throws Exception {
                FileDetails fileDetails =  notesService.getFileDetails(id);
               byte[] data =    notesService.downloadFile(fileDetails);
               HttpHeaders headers = new HttpHeaders();
               String contentType = CommonUtils.getContentType(fileDetails.getOriginalFileName());
               headers.setContentType(MediaType.parseMediaType(contentType));
               headers.setContentDispositionFormData("attachments", fileDetails.getOriginalFileName());
               return ResponseEntity.ok().headers(headers).body(data);
    }

    @Override
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> allNotes = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtils.createBuildResponse(allNotes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllNotesByUser(Integer pageNo, Integer pageSize){
        //int userId = 1;
        NotesResponse allNotesByUser = notesService.getAllNotesByUser(pageNo, pageSize);
        if(ObjectUtils.isEmpty(allNotesByUser)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtils.createBuildResponse(allNotesByUser, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> deleteNotes(Integer id){
            notesService.deleteNotes(id);
        return CommonUtils.createBuildResponseMessage("Notes deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> restoreNotes(Integer id){
        notesService.restoreNotes(id);
        return CommonUtils.createBuildResponseMessage("Notes restored successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserRecycleBinNotes(){
        //int userId = 1;
        List<NotesDto> notesDtos = notesService.getUserRecycleBinNotes();
        if(CollectionUtils.isEmpty(notesDtos)){
            return CommonUtils.createBuildResponseMessage("Recycle bin is empty", HttpStatus.OK);
        }
        return CommonUtils.createBuildResponse(notesDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> hardDeleteNotes(Integer id){
        notesService.hardDeleteNotes(id);
        return CommonUtils.createBuildResponseMessage("Notes deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> emptyRecycleBin(){
        //int userId = 1;
        notesService.emptyRecycleBin();
        return CommonUtils.createBuildResponseMessage("All Notes deleted successfully", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> getFavNotes(Integer noteId){
        notesService.favNotes(noteId);
        return CommonUtils.createBuildResponseMessage("Favourite Notes added!!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getUnFavNotes(Integer favId){
        notesService.unfavNote(favId);
        return CommonUtils.createBuildResponseMessage("Favourite Notes removed!!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserFavouriteNotes(){
        List<FavouriteNoteDto> userFavouriteNotes = notesService.getUserFavouriteNotes();
        if(CollectionUtils.isEmpty(userFavouriteNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtils.createBuildResponse(userFavouriteNotes, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> copyNotes(int id){
        Boolean copyingNotes =  notesService.copyNotes(id);
        if(copyingNotes){
            return CommonUtils.createBuildResponseMessage("Notes Copies successfully!!", HttpStatus.OK);
        }
        return CommonUtils.createErrorResponseMessage("Notes not copied !!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllNotesByUserSearch(String key,
                                                         Integer pageNo,
                                               Integer pageSize){
        //int userId = 1;
        NotesResponse allNotesByUser = notesService.getAllNotesByUserSearch(pageNo, pageSize, key);
        if(ObjectUtils.isEmpty(allNotesByUser)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtils.createBuildResponse(allNotesByUser, HttpStatus.OK);
    }


}
