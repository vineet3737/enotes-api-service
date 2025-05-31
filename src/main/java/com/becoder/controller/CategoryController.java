package com.becoder.controller;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/saveCategory")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        Boolean saveCategory = categoryService.saveCategory(categoryDto);

        if(saveCategory){
            return new ResponseEntity<>("saved", HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCategory")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        //String mn = null;
        //mn.toUpperCase();
        List<CategoryDto> allCategory = categoryService.getAllCategory();

        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        } else{
            return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/getActiveCategory")
    public ResponseEntity<List<CategoryResponse>> getActiveCategory(){
        List<CategoryResponse> allCategory = categoryService.getActiveCategory();

        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        } else{
            return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id){
        CategoryDto categoryById = categoryService.getCategoryById(id);

        if(ObjectUtils.isEmpty(categoryById)){
            return new ResponseEntity<>("Internal Server Error",HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(categoryById, HttpStatus.OK);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        Boolean deletedCategory = categoryService.deleteCategory(id);

        if(deletedCategory){
            return new ResponseEntity<>("Category Deleted Successfully !!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Category Not Deleted !!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
