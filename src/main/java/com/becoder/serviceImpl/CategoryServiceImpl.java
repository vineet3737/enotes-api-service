package com.becoder.serviceImpl;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.repository.CategoryRepository;
import com.becoder.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
     private CategoryRepository categoryRepository;

    @Autowired
     private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

        //Category category = new Category();
        //category.setName(categoryDto.getName());
        //category.setDescription(categoryDto.getDescription());
        //category.setIsActive(categoryDto.getIsActive());

        Category category = mapper.map(categoryDto, Category.class);

        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());
        Category saveCategory = categoryRepository.save(category);
        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream()
                    .map(category -> mapper.map(category, CategoryDto.class)).toList();
        return categoryDtos;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> activeCategories = categoryRepository.findByIsActiveTrue();
        List<CategoryResponse> activeCategoryDtos = activeCategories.stream().
                             map(category -> mapper.map(category, CategoryResponse.class)).toList();
        return activeCategoryDtos;
    }


}
