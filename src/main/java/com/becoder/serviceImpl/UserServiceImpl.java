package com.becoder.serviceImpl;

import com.becoder.dto.EmailRequest;
import com.becoder.dto.PasswordChangeRequest;
import com.becoder.dto.PasswordResetReq;
import com.becoder.entity.User;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.UserRepository;
import com.becoder.service.UserService;
import com.becoder.util.CommonUtils;
import com.becoder.util.EmailDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailDetails emailDetails;

    @Override
    public Boolean changePassword(PasswordChangeRequest passwordChangeRequest) {
        User user = CommonUtils.getLoggedInUser();
        String password = user.getPassword();
        if(passwordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
            userRepository.save(user);
            return true;
        }else {
            throw new ResourceNotFoundException("Password Not found!!");
        }
    }

    @Override
    public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception {

        User user = userRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(user)){
              throw new ResourceNotFoundException("Invalid User!!");
        }

        String passwordToken = UUID.randomUUID().toString();
        user.getStatus().setPasswordResetToken(passwordToken);
        User updatedUser = userRepository.save(user);
        String url = CommonUtils.getUrl(request);
        sendEmailRequest(updatedUser, url);

    }


    private void sendEmailRequest(User updatedUser, String url) throws Exception {
        String message = "Hi <b>[[username]]</b> "
                +"<br><p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=[[url]]>Change my password</a></p>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p><br>"
                + "Thanks,<br>Enotes.com";

        message = message.replace("[[username]]", updatedUser.getFirstName());
        message  = message.replace("[[url]]",
                url+"/api/v1/home/verify-pswd-link?uid=" + updatedUser.getId()
                        + "&&code=" + updatedUser.getStatus().getPasswordResetToken());


        EmailRequest emailRequest = EmailRequest.builder()
                .to(updatedUser.getEmail())
                .title("Password Reset")
                .subject("Password Reset Link")
                .message(message)
                .build();

        emailDetails.send(emailRequest);
    }

    @Override
    public void verifyPasswordResetLink(Integer uid, String code) {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid User!!"));
        verifyPasswordResetToken(user.getStatus().getPasswordResetToken(), code);
    }



    private void verifyPasswordResetToken(String existToken, String reqToken) {

        if(StringUtils.hasText(reqToken)){
            if(!StringUtils.hasText(existToken)){
                throw new IllegalArgumentException("Password already reset");
            }
            if(!existToken.equals(reqToken)){
                throw new IllegalArgumentException("Invalid URL");
            }

        }else{
            throw new IllegalArgumentException("Invalid Token");
        }
    }


    @Override
    public void resetPassword(PasswordResetReq passwordResetReq) {
        User user = userRepository.
                findById(passwordResetReq.getUid()).orElseThrow(() -> new ResourceNotFoundException("Invalid User"));
        String encodedPassword = passwordEncoder.encode(passwordResetReq.getNewPassword());
        user.setPassword(encodedPassword);
        user.getStatus().setPasswordResetToken(null);
        userRepository.save(user);
    }
}
