package com.becoder.serviceImpl;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ExistsDataException;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.CategoryRepository;
import com.becoder.service.CacheManagerService;
import com.becoder.service.CategoryService;
import com.becoder.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
     private CategoryRepository categoryRepository;

    @Autowired
     private ModelMapper mapper;

    @Autowired
     private Validation validation;

     @Autowired
     private CacheManagerService cacheService;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

        //Category category = new Category();
        //category.setName(categoryDto.getName());
        //category.setDescription(categoryDto.getDescription());
        //category.setIsActive(categoryDto.getIsActive());
        validation.categoryValidation(categoryDto);

        Boolean existByName = categoryRepository.existsByName(categoryDto.getName().trim());
        if(existByName){
            throw new ExistsDataException("Category with this name already exists");
        }

        Category category = mapper.map(categoryDto, Category.class);

        if(ObjectUtils.isEmpty(category.getId())){
            category.setIsDeleted(false);
           // category.setCreatedBy(1);
            category.setCreatedOn(new Date());
        }else{
            updateCategory(category);
        }
        Category saveCategory = categoryRepository.save(category);
        return !ObjectUtils.isEmpty(saveCategory);
    }

    private void updateCategory(Category category) {
        Optional<Category> categoryById = categoryRepository.findById(category.getId());
        if(categoryById.isPresent())
        {
            Category category1 = categoryById.get();
            category.setCreatedBy(category1.getCreatedBy());
            category.setCreatedOn(category1.getCreatedOn());
            category.setIsDeleted(category1.getIsDeleted());
           // category.setUpdatedBy(1);
            //category.setUpdatedOn(new Date());
        }
    }

    @Override
    @Cacheable("allCategory")
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        List<CategoryDto> categoryDtos = categories.stream()
                    .map(category -> mapper.map(category, CategoryDto.class)).toList();
        //List<Object> list = new ArrayList<>();
//        for(CategoryDto l: categoryDtos){
//             List<CategoryDto> lo = new ArrayList<>();
//             lo.add(l);
//             lo.stream().map(leg -> leg.getId()).filter(num -> num % 2 ==0).forEach(System.out::println);
//        }
        return categoryDtos;
    }

    @Override
    @Cacheable("activeCategory")
    public List<CategoryResponse> getActiveCategory() {
        List<Category> activeCategories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> activeCategoryDtos = activeCategories.stream().
                             map(category -> mapper.map(category, CategoryResponse.class)).toList();
        return activeCategoryDtos;
    }

    @Override
    @Cacheable(value = "getCategoryById", key = "#id")
    public CategoryDto getCategoryById(Integer id) {
//        Optional<Category> getById = categoryRepository.findByIdAndIsDeletedFalse(id);
//        if(getById.isPresent()){
//            Category category = getById.get();
//            return mapper.map(category, CategoryDto.class);
//        }
//        return null;
        Category getCategoryById = categoryRepository.
                findByIdAndIsDeletedFalse(id).orElseThrow(() ->
                        new ResourceNotFoundException("Category Id not found "+id));
        if(!ObjectUtils.isEmpty(getCategoryById)){
            return mapper.map(getCategoryById, CategoryDto.class);
        }
        return null;
    }

    @Override
    @CacheEvict(value = "getCategoryById", key = "#id")
    public Boolean deleteCategory(Integer id) {
        Optional<Category> getByIdNew = categoryRepository.findById(id);
        if(getByIdNew.isPresent()){
            Category categoryNew = getByIdNew.get();
            categoryNew.setIsDeleted(true);
            categoryRepository.save(categoryNew);

            //remove from cache
            cacheService.removeCacheByName(Arrays.asList("allCategory", "activeCategory"));
            return true;
        }
        return false;
    }


}
