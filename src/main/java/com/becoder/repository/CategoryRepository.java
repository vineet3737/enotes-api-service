package com.becoder.repository;

import com.becoder.dto.CategoryDto;
import com.becoder.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


    public List<Category> findByIsActiveTrue();

    Optional<Category> findByIdAndIsDeletedFalse(Integer id);

    List<Category> findByIsActiveTrueAndIsDeletedFalse();

    List<Category> findByIsDeletedFalse();
}
