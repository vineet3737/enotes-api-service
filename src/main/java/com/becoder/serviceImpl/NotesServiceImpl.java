package com.becoder.serviceImpl;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.entity.FileDetails;
import com.becoder.entity.Notes;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.CategoryRepository;
import com.becoder.repository.FileRepository;
import com.becoder.repository.NotesRepos;
import com.becoder.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepos notesRepos;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileRepository fileRepo;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto = ob.readValue(notes, NotesDto.class);
        notesDto.setDeleted(false);
        notesDto.setDeletedOn(null);

        if (!ObjectUtils.isEmpty(notesDto.getId())) {
            updateNotes(notesDto, file);
        }

        //Category Validation
        checkCategoryExists(notesDto.getCategory());
        Notes notesNew = mapper.map(notesDto, Notes.class);

        FileDetails fileDetails =  saveFileDetails(file);
        if(!ObjectUtils.isEmpty(fileDetails)){
            notesNew.setFileDetails(fileDetails);
        }else{
            if (ObjectUtils.isEmpty(notesDto.getId())) {

                notesNew.setFileDetails(null);
            }

        }

        Notes saveNotes = notesRepos.save(notesNew);
        if(!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }
        return false;
    }

    private void updateNotes(NotesDto notesDto, MultipartFile file) {
        Notes existNotes = notesRepos.findById(notesDto.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Invalid Notes Id"));

        if(ObjectUtils.isEmpty(file)) {
            //existNotes.setFileDetails(mapper.map(notesDto.getFileDetails(), NotesDto.FileDto.class));
            notesDto.setFileDetails(mapper.map(existNotes.getFileDetails(), NotesDto.FileDto.class));
        }
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {

          if(!ObjectUtils.isEmpty(file) && !file.isEmpty()){
              String originalFilename = file.getOriginalFilename();
              String extension = FilenameUtils.getExtension(originalFilename);
              List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpeg","png");
              if(!extensionAllow.contains(extension)){
                  throw new IllegalArgumentException("Invalid File Format");
              }
              String rndString = UUID.randomUUID().toString();
              String uploadFileName = rndString+"."+extension;
              File saveFile = new File(uploadPath);

              if(!saveFile.exists()){
                  saveFile.mkdir();
              }
              String storePath = uploadPath.concat(uploadFileName);


              //upload file
              long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
              if(upload!=0){
                  FileDetails filedtls = new FileDetails();
                  filedtls.setOriginalFileName(originalFilename);
                  filedtls.setDisplayFileName(getDisplayName(originalFilename));
                  filedtls.setUploadFileName(uploadFileName);
                  filedtls.setFileSize(file.getSize());
                  filedtls.setPath(storePath);
                  FileDetails saveFileDetails = fileRepo.save(filedtls);
                  return saveFileDetails;
              }

          }
          return null;
    }

    private String getDisplayName(String originalFilename) {
        //java_programming_tutorials.pdf

        String extension = FilenameUtils.getExtension(originalFilename);
        String fileName = FilenameUtils.removeExtension(originalFilename);

        if(fileName.length() > 8){
            fileName = fileName.substring(0, 7);
        }
        fileName = fileName+"."+extension;

        return fileName;

    }


    private void checkCategoryExists(NotesDto.CategoryDto category) {

           categoryRepository.findById(category.getId())
                  .orElseThrow(() -> new ResourceNotFoundException("Category does not exits!!"));
    }

    @Override
    public List<NotesDto> getAllNotes() {
        List<Notes> allNotes = notesRepos.findAll();
        List<NotesDto> notesDtos = allNotes.stream()
                .map(note -> mapper.map(note, NotesDto.class)).toList();
        return notesDtos;
    }

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws IOException {
        InputStream io = new FileInputStream(fileDetails.getPath());
        return StreamUtils.copyToByteArray(io);
    }

    @Override
    public FileDetails getFileDetails(Integer id) {
        FileDetails fileDetails = fileRepo.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("File with given id not present"));
        return fileDetails;
    }

    @Override
    public NotesResponse getAllNotesByUser(int userId, int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Notes> notesByPage = notesRepos.findByCreatedByAndIsDeletedFalse(userId, pageable);

        List<NotesDto> notesDtos = notesByPage.get().map(m -> mapper.map(m, NotesDto.class)).toList();

        NotesResponse notesResponse = NotesResponse.builder()
                .notes(notesDtos)
                .pageNo(notesByPage.getNumber())
                .pageSize(notesByPage.getSize())
                .totalElements(notesByPage.getTotalElements())
                .totalPages(notesByPage.getTotalPages())
                .isFirst(notesByPage.isFirst())
                .isLast(notesByPage.isLast())
                .build();

        return notesResponse;
    }

    @Override
    public void deleteNotes(Integer id) {
        Notes notes = notesRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notes not found with given id " + id));
        notes.setDeleted(true);
        notes.setDeletedOn(LocalDateTime.now());
        notesRepos.save(notes);
    }

    @Override
    public void restoreNotes(Integer id) {
        Notes notes = notesRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notes not found with given id " + id));
        notes.setDeleted(false);
        notes.setDeletedOn(null);
        notesRepos.save(notes);
    }

    @Override
    public List<NotesDto> getUserRecycleBinNotes(int userId) {

        List<Notes> notes = notesRepos.findByCreatedByAndIsDeletedTrue(userId);
        List<NotesDto> notesDtos = notes.stream().map(note -> mapper.map(note, NotesDto.class)).toList();
        return notesDtos;
    }

    @Override
    public void hardDeleteNotes(Integer id) {
        Notes notes = notesRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notes not found with given id " + id));

        if(notes.isDeleted()){
            notesRepos.delete(notes);
        }else{
            throw new IllegalArgumentException("Sorry you cant hard delete it !!");
        }
    }

    @Override
    public void emptyRecycleBin(int id) {
          List<Notes> notes =   notesRepos.findByCreatedByAndIsDeletedTrue(id);
          if(!CollectionUtils.isEmpty(notes)){
              notesRepos.deleteAll(notes);
          }else{
              throw new IllegalArgumentException("Sorry there is no data in recycle bin");
          }


    }


}
