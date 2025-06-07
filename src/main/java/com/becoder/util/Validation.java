package com.becoder.util;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.ToDoDto;
import com.becoder.dto.UserDto;
import com.becoder.entity.User;
import com.becoder.enums.ToDoStatus;
import com.becoder.exception.ExistsDataException;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.RoleRepository;
import com.becoder.repository.UserRepository;
import com.becoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class Validation {

    @Autowired
    private RoleRepository roleRepos;

    @Autowired
    private UserRepository userRepos;


    public void categoryValidation(CategoryDto categoryDto){

        Map<String, Object> error = new LinkedHashMap<>();

        if(ObjectUtils.isEmpty(categoryDto)){
                    throw new IllegalArgumentException("Category Object/Json should not be null or empty");
        }else{
            if(ObjectUtils.isEmpty(categoryDto.getName())){
                error.put("name", "name field is empty or null");
            }else{
                if(categoryDto.getName().length() < 5){
                    error.put("name", "name length min 5");
                }
                if(categoryDto.getName().length() > 100){
                    error.put("name", "name length max 100");
                }
            }
               //Description Validation
            if(ObjectUtils.isEmpty(categoryDto.getDescription())){
                error.put("description", "description field is empty or null");
            }
            //IsActive Validation
            if(ObjectUtils.isEmpty(categoryDto.getIsActive())){
                error.put("isActive", "isActive field is empty or null");
            }else{
                if(categoryDto.getIsActive() != Boolean.FALSE.booleanValue() &&
                        categoryDto.getIsActive() != Boolean.TRUE.booleanValue()){
                    error.put("isActive", "Invalid value isActive field");
                }

            }

        }
             if(!error.isEmpty()){
                 throw new ValidationException(error);
             }
    }

    public void todoValidation(ToDoDto toDo){
        ToDoDto.StatusDto requestStatus = toDo.getStatus();
        ToDoStatus[] status = ToDoStatus.values();
        Boolean statusFound = false;
        for(ToDoStatus st : status){
            if(st.getId().equals(requestStatus.getId())){
                statusFound = true;
            }
        }
        if(!statusFound){
            throw new ResourceNotFoundException("Invalid Status");
        }
    }

    public void userValidation(UserDto userDto){

        if(!StringUtils.hasText(userDto.getFirstName())){
            throw new IllegalArgumentException("first name is invalid!!");
        }
        if(!StringUtils.hasText(userDto.getLastName())){
            throw new IllegalArgumentException("last name is invalid!!");
        }
        if(!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX)){
            throw new IllegalArgumentException("email is invalid!!");
        }else {
               Boolean existEmail = userRepos.existsByEmail(userDto.getEmail());
               if(existEmail){
                   throw new ExistsDataException("User already exists");
               }
        }
        if(!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constants.MOBNO_REGEX)){
            throw new IllegalArgumentException("mobNo is invalid!!");
        }

        if(ObjectUtils.isEmpty(userDto.getRoles())){
            throw new IllegalArgumentException("Role is invalid!!");
        }else{
            List<Integer> roleIds = roleRepos.findAll().stream().map(r -> r.getId()).toList();
            List<Integer> invalidRoleIds = userDto.getRoles()
                                 .stream().map(r -> r.getId()).filter(roleId -> !roleIds.contains(roleId))
                                 .toList();
             if(!CollectionUtils.isEmpty(invalidRoleIds)){
                 throw new IllegalArgumentException("Role is Invalid" + invalidRoleIds);
             }

        }
    }
}
