package com.becoder.endpoint;

import com.becoder.dto.PasswordChangeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/user")
public interface UserEndpoint {

    @GetMapping("/profile")
    public ResponseEntity<?> getLoggedInUser();

    @GetMapping("/passwordChange")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);
}
