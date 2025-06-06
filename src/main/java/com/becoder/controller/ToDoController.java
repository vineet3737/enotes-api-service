package com.becoder.controller;

import com.becoder.dto.ToDoDto;
import com.becoder.service.ToDoService;
import com.becoder.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @PostMapping("/saveToDo")
    public ResponseEntity<?> saveToDo(@RequestBody ToDoDto toDoDto){
        Boolean saveToDo = toDoService.saveToDo(toDoDto);
        if(saveToDo){
            return CommonUtils.createBuildResponseMessage("ToDo saved successfully", HttpStatus.CREATED);
        }
        return CommonUtils.createErrorResponseMessage("ToDo not saved !!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getTodo/{id}")
    public ResponseEntity<?> getToDoById(@PathVariable Integer id){
        ToDoDto toDoById = toDoService.getToDoById(id);
        return CommonUtils.createBuildResponse(toDoById, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllTodo(){
        List<ToDoDto> toDoList = toDoService.getToDoByUser();
        if(CollectionUtils.isEmpty(toDoList)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtils.createBuildResponse(toDoList, HttpStatus.OK);
    }
}
