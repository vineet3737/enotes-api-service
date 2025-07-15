package com.becoder.controller;

import com.becoder.dto.PasswordChangeRequest;
import com.becoder.dto.UserResponse;
import com.becoder.endpoint.UserEndpoint;
import com.becoder.entity.User;
import com.becoder.service.UserService;
import com.becoder.util.CommonUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserEndpoint {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;


      @Override
      public ResponseEntity<?> getLoggedInUser(){
          User loggedInUser = CommonUtils.getLoggedInUser();
          UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
          return CommonUtils.createBuildResponse(userResponse, HttpStatus.OK);
      }

    @Override
    public ResponseEntity<?> changePassword(PasswordChangeRequest passwordChangeRequest){
        Boolean changPasswd = userService.changePassword(passwordChangeRequest);
        if(changPasswd){
            return CommonUtils.createBuildResponseMessage("Password Changed Successfully !!", HttpStatus.OK);
        }else{
            return CommonUtils.createErrorResponseMessage("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
