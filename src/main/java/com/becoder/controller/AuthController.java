package com.becoder.controller;

import com.becoder.dto.LoginRequest;
import com.becoder.dto.LoginResponse;
import com.becoder.dto.UserDto;
import com.becoder.endpoint.AuthEndpoint;
import com.becoder.service.AuthService;
import com.becoder.util.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthEndpoint {

    @Autowired
    private AuthService userService;

    @Override
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
        String url = CommonUtils.getUrl(request);
        Boolean saveUser = userService.registerUser(userDto, url);
        if(saveUser){
            return CommonUtils.createBuildResponseMessage("User registered successfully", HttpStatus.CREATED);
        }
        return CommonUtils.createErrorResponseMessage("Notes not saved !!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

   @Override
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = userService.login(loginRequest);
           if(ObjectUtils.isEmpty(loginResponse)){
               return CommonUtils.createErrorResponseMessage("Invalid Credentials", HttpStatus.BAD_REQUEST);
           }
               return CommonUtils.createBuildResponse(loginResponse, HttpStatus.OK);

    }
}
