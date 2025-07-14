package com.becoder.endpoint;

import com.becoder.dto.PasswordResetReq;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code);

    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception;

    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception;

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetReq passwordResetReq) throws Exception;
}
