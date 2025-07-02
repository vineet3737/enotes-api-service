package com.becoder.serviceImpl;

import com.becoder.dto.PasswordChangeRequest;
import com.becoder.entity.User;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.UserRepository;
import com.becoder.service.UserService;
import com.becoder.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
}
