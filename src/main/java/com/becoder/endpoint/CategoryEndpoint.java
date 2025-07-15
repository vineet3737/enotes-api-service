package com.becoder.endpoint;

import com.becoder.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category", description = "All the Category operation APIs")
@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @Operation(summary = "Save Category", tags = {"Category"}, description = "Admin Save Category")
    @PostMapping("/saveCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);

    @Operation(summary = "Get All Category", tags = {"Category"}, description = "Admin Get All Category")
    @GetMapping("/getCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCategory();

    @Operation(summary = "Get Active Category", tags = {"Category"}, description = "Admin,User Get Active Category")
    @GetMapping("/getActiveCategory")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getActiveCategory();

    @Operation(summary = "Get Category By Id", tags = {"Category"}, description = "Admin Get Category Details")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id);

    @Operation(summary = "Delete Category", tags = {"Category"}, description = "Admin Delete Category")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Integer id);
}
