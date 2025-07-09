package com.becoder.repository;

import com.becoder.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByCreatedBy(int userId);
}
