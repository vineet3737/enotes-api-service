package com.becoder.controller;

import com.becoder.dto.PasswordResetReq;
import com.becoder.endpoint.HomeEndpoint;
import com.becoder.service.HomeService;
import com.becoder.service.UserService;
import com.becoder.util.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class HomeController implements HomeEndpoint {

    @Autowired
    private HomeService service;

    @Autowired
    private UserService userService;

     @Override
    public ResponseEntity<?> verifyUserAccount(Integer uid, String code){
        Boolean verifiedAccount = service.verifyAccount(uid, code);
        if(verifiedAccount){
            return CommonUtils.createBuildResponseMessage("User verified successfully", HttpStatus.OK);
        }
        return CommonUtils.createErrorResponseMessage("Invalid Verification Link", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception {
        userService.sendEmailPasswordReset(email, request);
        return CommonUtils.createBuildResponseMessage("Email Send Success !! Check Email to Reset Password", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> verifyPasswordResetLink(Integer uid, String code) throws Exception {
        userService.verifyPasswordResetLink(uid, code);
        return CommonUtils.createBuildResponseMessage("Verification Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> resetPassword(PasswordResetReq passwordResetReq) throws Exception {
        userService.resetPassword(passwordResetReq);
        return CommonUtils.createBuildResponseMessage("Password Reset Successfully!!", HttpStatus.OK);
    }
}
