package com.cursor.demo.service;

import com.cursor.demo.model.dto.UserDto;
import com.cursor.demo.model.entity.UserEntity;

public interface UserService
{
    Long save(UserDto userDto);
    UserEntity findByUsername(String username);
}
