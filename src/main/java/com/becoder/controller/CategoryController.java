package com.becoder.controller;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.service.CategoryService;
//import jakarta.validation.Valid;
import com.becoder.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        Boolean saveCategory = categoryService.saveCategory(categoryDto);

        if(saveCategory){
            return CommonUtils.createBuildResponseMessage("saved", HttpStatus.CREATED);
           // return new ResponseEntity<>("saved", HttpStatus.CREATED);
        } else{
            return CommonUtils.createErrorResponseMessage("Category not saved", HttpStatus.INTERNAL_SERVER_ERROR);
            //return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCategory(){
        //String mn = null;
        //mn.toUpperCase();
        List<CategoryDto> allCategory = categoryService.getAllCategory();

        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        } else{
            return CommonUtils.createBuildResponse(allCategory, HttpStatus.OK);
           // return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/getActiveCategory")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse> allCategory = categoryService.getActiveCategory();

        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        } else{
            //return new ResponseEntity<>(allCategory, HttpStatus.OK);
            return CommonUtils.createBuildResponse(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id){
        CategoryDto categoryById = categoryService.getCategoryById(id);

        if(ObjectUtils.isEmpty(categoryById)){
           // return new ResponseEntity<>("Internal Server Error",HttpStatus.NOT_FOUND);
            return CommonUtils.createErrorResponseMessage("Internal Server Error", HttpStatus.NOT_FOUND);
        } else{
           // return new ResponseEntity<>(categoryById, HttpStatus.OK);
            return CommonUtils.createBuildResponse(categoryById, HttpStatus.OK);
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        Boolean deletedCategory = categoryService.deleteCategory(id);

        if(deletedCategory){
           // return new ResponseEntity<>("Category Deleted Successfully !!", HttpStatus.OK);
            return CommonUtils.createBuildResponse("Category Deleted Successfully !!", HttpStatus.OK);
        }
        //return new ResponseEntity<>("Category Not Deleted !!", HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtils.createErrorResponseMessage("Category Not Deleted !!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
