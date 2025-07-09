package com.becoder.serviceImpl;

import com.becoder.dto.ToDoDto;
import com.becoder.entity.Todo;
import com.becoder.enums.ToDoStatus;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.ToDoRepository;
import com.becoder.service.ToDoService;
import com.becoder.util.CommonUtils;
import com.becoder.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    private ToDoRepository toDoRepos;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;


    @Override
    public Boolean saveToDo(ToDoDto todoDto) {
            //validate todo status
        validation.todoValidation(todoDto);
        Todo toDo = mapper.map(todoDto, Todo.class);
        toDo.setStatusId(todoDto.getStatus().getId());
        Todo saveTodo = toDoRepos.save(toDo);
        if(!ObjectUtils.isEmpty(saveTodo)){
            return true;
        }
        return false;
    }

    @Override
    public ToDoDto getToDoById(Integer id) {
        Todo toDo = toDoRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ToDo is not found!!"));
        ToDoDto dtoTodo = mapper.map(toDo, ToDoDto.class);
        setStatus(dtoTodo, toDo);
        return dtoTodo;

    }

    private void setStatus(ToDoDto dtoTodo, Todo toDo) {
        Integer id = toDo.getStatusId();
        ToDoStatus[] values = ToDoStatus.values();
        for(ToDoStatus st : values){
                 if(st.getId().equals(id)){
                     ToDoDto.StatusDto statusDto = ToDoDto.StatusDto.builder()
                             .id(st.getId())
                             .name(st.getName())
                             .build();
                     dtoTodo.setStatus(statusDto);
                 }
        }
    }

    @Override
    public List<ToDoDto> getToDoByUser() {
        //int userId = 1;
        Integer userId = CommonUtils.getLoggedInUser().getId();
        List<Todo> todoList = toDoRepos.findByCreatedBy(userId);
        List<ToDoDto> toDoDtos = todoList.stream()
                .map(todo -> mapper.map(todo, ToDoDto.class)).toList();
        return toDoDtos;
    }
}
