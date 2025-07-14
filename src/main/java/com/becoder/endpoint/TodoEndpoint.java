package com.becoder.endpoint;

import com.becoder.dto.ToDoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @PostMapping("/saveToDo")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveToDo(@RequestBody ToDoDto toDoDto);

    @GetMapping("/getTodo/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getToDoById(@PathVariable Integer id);

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllTodo();
}
