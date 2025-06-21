package com.becoder.serviceImpl;

import com.becoder.dto.EmailRequest;
import com.becoder.dto.LoginRequest;
import com.becoder.dto.LoginResponse;
import com.becoder.dto.UserDto;
import com.becoder.entity.AccountStatus;
import com.becoder.entity.Role;
import com.becoder.entity.User;
import com.becoder.repository.RoleRepository;
import com.becoder.repository.UserRepository;
import com.becoder.security.CustomUserDetails;
import com.becoder.service.UserService;
import com.becoder.util.EmailDetails;
import com.becoder.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepos;

    @Autowired
    private RoleRepository roleRepos;

    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailDetails emailDetails;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Boolean registerUser(UserDto userDto, String url) throws Exception {
        validation.userValidation(userDto);
        User user = mapper.map(userDto, User.class);
        setRole(userDto, user);
        AccountStatus accountStatus = AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
         user.setStatus(accountStatus);
         user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saveUser = userRepos.save(user);
        if(!ObjectUtils.isEmpty(saveUser)){
            //Send Email
            emailSend(saveUser, url);
            return true;
        }
        return false;
    }

    private void emailSend(User saveUser, String url) throws Exception {

        String message="Hi,<b>[[username]]</b> "
                + "<br><br> Your account register sucessfully.<br>"
                +"<br> Click the below link verify & Activate your account <br>"
                +"<a href='[[url]]'>Click Here</a> <br><br>"
                +"Thanks,<br>Enotes.com"
                ;

        message = message.replace("[[username]]", saveUser.getFirstName());
        message  = message.replace("[[url]]",
                url+"/api/v1/home/verify?uid=" + saveUser.getId() + "&&code=" + saveUser.getStatus().getVerificationCode());


        EmailRequest emailRequest = EmailRequest.builder()
                .to(saveUser.getEmail())
                .title("Account Creating Confirmation")
                .subject("Account Created Success")
                .message(message)
                .build();

        emailDetails.send(emailRequest);

    }

    private void setRole(UserDto userDto, User user) {
        List<Integer> roleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> roleList = roleRepos.findAllById(roleId);
        user.setRoles(roleList);

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if(authenticate.isAuthenticated()){
            CustomUserDetails customUserDetails =
                    (CustomUserDetails)authenticate.getPrincipal();
            String token = "vchjbkcjckbjcbekbcjekcbkejckejcbk";
            LoginResponse loginResponse = LoginResponse.builder()
                    .user(mapper.map(customUserDetails.getUser(), UserDto.class))
                    .token(token)
                    .build();
            return loginResponse;
        }
        return null;
    }
}
