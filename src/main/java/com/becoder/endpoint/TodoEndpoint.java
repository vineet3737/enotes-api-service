package com.becoder.endpoint;

import com.becoder.dto.ToDoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Todo", description = "All the Todo Operation APIs")
@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @Operation(summary = "Save Todo", tags = { "Notes" }, description = "Save Todo")
    @PostMapping("/saveToDo")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveToDo(@RequestBody ToDoDto toDoDto);

    @Operation(summary = "Get Todo", tags = { "Notes" }, description = "Get Todo")
    @GetMapping("/getTodo/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getToDoById(@PathVariable Integer id);

    @Operation(summary = "Get All Todo By User", tags = { "Notes" }, description = "Get All Todo By User")
    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllTodo();
}
