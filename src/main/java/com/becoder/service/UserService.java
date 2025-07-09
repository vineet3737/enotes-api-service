package com.becoder.service;

import com.becoder.dto.PasswordChangeRequest;
import com.becoder.dto.PasswordResetReq;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    public Boolean changePassword(PasswordChangeRequest passwordChangeRequest);

    void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception;

    void verifyPasswordResetLink(Integer uid, String code);

    void resetPassword(PasswordResetReq passwordResetReq);
}
