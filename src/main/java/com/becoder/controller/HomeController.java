package com.becoder.controller;

import com.becoder.service.HomeService;
import com.becoder.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService service;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code){
        Boolean verifiedAccount = service.verifyAccount(uid, code);
        if(verifiedAccount){
            return CommonUtils.createBuildResponseMessage("User verified successfully", HttpStatus.OK);
        }
        return CommonUtils.createErrorResponseMessage("Invalid Verification Link", HttpStatus.BAD_REQUEST);
    }
}
