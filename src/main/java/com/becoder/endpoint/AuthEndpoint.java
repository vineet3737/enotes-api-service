package com.becoder.endpoint;

import com.becoder.dto.LoginRequest;
import com.becoder.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception;
}
