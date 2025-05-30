package com.becoder.controller;

import com.becoder.entity.Category;
import com.becoder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/saveCategory")
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        Boolean saveCategory = categoryService.saveCategory(category);

        if(saveCategory){
            return new ResponseEntity<>("saved", HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCategory")
    public ResponseEntity<List<Category>> getAllCategory(){
        List<Category> allCategory = categoryService.getAllCategory();

        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        } else{
            return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }


}
