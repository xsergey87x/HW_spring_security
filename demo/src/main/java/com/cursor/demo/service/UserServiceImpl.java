package com.cursor.demo.service;

import com.cursor.demo.model.dto.UserDto;
import com.cursor.demo.model.entity.UserEntity;
import com.cursor.demo.repository.UserRepository;
import com.cursor.demo.service.objectMapper.ObjectMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long save(UserDto userDto) {
        UserEntity user = ObjectMapper.mapFromDtoToEntity(userDto);
        String encodedPassword = new BCryptPasswordEncoder(12).encode(user.getPassword());
        user.setPassword(encodedPassword);
        UserEntity savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format("User with username=%s not found.", username)));
    //    return null;
    }
}
