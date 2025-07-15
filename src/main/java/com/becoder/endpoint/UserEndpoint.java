package com.becoder.endpoint;

import com.becoder.dto.PasswordChangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User", description = "Authentication User Operation APIs")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {

    @Operation(summary = "Get User Profile", tags = { "Notes" }, description = "Get User Profile")
    @GetMapping("/profile")
    public ResponseEntity<?> getLoggedInUser();

    @Operation(summary = "User Account Password Change", tags = {
            "Notes" }, description = "User Account Password Change")
    @GetMapping("/passwordChange")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);
}
