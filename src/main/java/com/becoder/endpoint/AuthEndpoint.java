package com.becoder.endpoint;

import com.becoder.dto.LoginRequest;
import com.becoder.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication", description = "All the user authentication APIs")
@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Register Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server error"),
            @ApiResponse(responseCode = "400", description = "Bad Request") })

    @Operation(summary = "User Register EndPoint", tags = { "Authentication", "Home" })
    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception;

    @Operation(summary = "User Login EndPoint", tags = { "Authentication", "Home" })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception;
}
