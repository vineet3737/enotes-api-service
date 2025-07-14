package com.becoder.endpoint;

import com.becoder.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @PostMapping("/saveCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);

    @GetMapping("/getCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCategory();

    @GetMapping("/getActiveCategory")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getActiveCategory();

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Integer id);
}
