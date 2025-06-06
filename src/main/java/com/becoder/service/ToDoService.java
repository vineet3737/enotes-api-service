package com.becoder.service;

import com.becoder.dto.ToDoDto;

import java.util.List;

public interface ToDoService {

    public Boolean saveToDo(ToDoDto toDoDto);

    public ToDoDto getToDoById(Integer id);

    public List<ToDoDto> getToDoByUser();
}
