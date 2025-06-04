package com.becoder.serviceImpl;

import com.becoder.dto.NotesDto;
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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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


        //Category Validation
        checkCategoryExists(notesDto.getCategory());
        Notes notesNew = mapper.map(notesDto, Notes.class);

        FileDetails fileDetails =  saveFileDetails(file);
        if(!ObjectUtils.isEmpty(fileDetails)){
            notesNew.setFileDetails(fileDetails);
        }else{
            notesNew.setFileDetails(null);
        }

        Notes saveNotes = notesRepos.save(notesNew);
        if(!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }
        return false;
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


}
