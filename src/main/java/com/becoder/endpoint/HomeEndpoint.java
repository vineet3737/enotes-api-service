package com.becoder.endpoint;

import com.becoder.dto.PasswordResetReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Home", description = "All the Home APIs")
@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

    @Operation(summary = "Verification user account", tags = {"Home"}, description = "User Account verification after register account")
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code);

    @Operation(summary = "Send Email for Password Reset", tags = {"Home"}, description = "User Can send Email for password reset")
    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception;

    @Operation(summary = "Verification password link", tags = {"Home"}, description = "User verification password link")
    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception;

    @Operation(summary = "Reset Password", tags = { "Home" }, description = "User Can changes Password")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetReq passwordResetReq) throws Exception;
}
