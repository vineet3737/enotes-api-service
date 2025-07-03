package com.becoder.controller;

import com.becoder.dto.PasswordResetReq;
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
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService service;

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code){
        Boolean verifiedAccount = service.verifyAccount(uid, code);
        if(verifiedAccount){
            return CommonUtils.createBuildResponseMessage("User verified successfully", HttpStatus.OK);
        }
        return CommonUtils.createErrorResponseMessage("Invalid Verification Link", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception {
        userService.sendEmailPasswordReset(email, request);
        return CommonUtils.createBuildResponseMessage("Email Send Success !! Check Email to Reset Password", HttpStatus.OK);
    }

    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception {
        userService.verifyPasswordResetLink(uid, code);
        return CommonUtils.createBuildResponseMessage("Verification Success", HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetReq passwordResetReq) throws Exception {
        userService.resetPassword(passwordResetReq);
        return CommonUtils.createBuildResponseMessage("Password Reset Successfully!!", HttpStatus.OK);
    }
}
