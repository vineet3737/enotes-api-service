package com.becoder.serviceImpl;

import com.becoder.dto.UserDto;
import com.becoder.entity.Role;
import com.becoder.entity.User;
import com.becoder.repository.RoleRepository;
import com.becoder.repository.UserRepository;
import com.becoder.service.UserService;
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

    @Override
    public Boolean registerUser(UserDto userDto) {
        validation.userValidation(userDto);
        User user = mapper.map(userDto, User.class);
        setRole(userDto, user);
        User saveUser = userRepos.save(user);
        if(!ObjectUtils.isEmpty(saveUser)){
            return true;
        }
        return false;
    }

    private void setRole(UserDto userDto, User user) {
        List<Integer> roleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> roleList = roleRepos.findAllById(roleId);
        user.setRoles(roleList);

    }
}
