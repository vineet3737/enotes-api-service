package com.becoder.service;

import com.becoder.dto.UserDto;

public interface UserService {

    public Boolean registerUser(UserDto userDto) throws Exception;
}
