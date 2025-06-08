package com.becoder.controller;

import com.becoder.dto.UserDto;
import com.becoder.service.UserService;
import com.becoder.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) throws Exception {
        Boolean saveUser = userService.registerUser(userDto);
        if(saveUser){
            return CommonUtils.createBuildResponseMessage("User registered successfully", HttpStatus.CREATED);
        }
        return CommonUtils.createErrorResponseMessage("Notes not saved !!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
