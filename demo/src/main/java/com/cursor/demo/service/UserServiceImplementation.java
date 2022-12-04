package com.cursor.demo.service;

import com.cursor.demo.model.dto.UserDto;
import com.cursor.demo.model.entity.UserEntity;
import com.cursor.demo.repository.UserRepository;
import com.cursor.demo.service.objectMapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService{
    private final UserRepository userRepository;

    @Override
    public Long save(UserDto userDto) {
        UserEntity user = ObjectMapper.mapFromDtoToEntity(userDto);
        String encodedPassword = new BCryptPasswordEncoder(12).encode(user.getPassword());
        user.setPassword(encodedPassword);
        UserEntity savedUser = userRepository.saveAndFlush(user);
        return savedUser.getId();
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format("User with username=%s not found.", username)));
    }
}
