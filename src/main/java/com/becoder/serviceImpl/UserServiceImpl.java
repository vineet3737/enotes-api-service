package com.becoder.serviceImpl;

import com.becoder.dto.EmailRequest;
import com.becoder.dto.UserDto;
import com.becoder.entity.Role;
import com.becoder.entity.User;
import com.becoder.repository.RoleRepository;
import com.becoder.repository.UserRepository;
import com.becoder.service.UserService;
import com.becoder.util.EmailDetails;
import com.becoder.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

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

    @Override
    public Boolean registerUser(UserDto userDto) throws Exception {
        validation.userValidation(userDto);
        User user = mapper.map(userDto, User.class);
        setRole(userDto, user);
        User saveUser = userRepos.save(user);
        if(!ObjectUtils.isEmpty(saveUser)){
            //Send Email
            emailSend(saveUser);
            return true;
        }
        return false;
    }

    private void emailSend(User saveUser) throws Exception {

        String message="Hi,<b>"+saveUser.getFirstName()+"</b> "
                + "<br><br> Your account register sucessfully.<br>"
                +"<br> Click the below link verify & Activate your account <br>"
                +"<a href='#'>Click Here</a> <br><br>"
                +"Thanks,<br>Enotes.com"
                ;


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
}
