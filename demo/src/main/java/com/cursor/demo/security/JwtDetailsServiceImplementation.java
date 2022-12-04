package com.cursor.demo.security;

import com.cursor.demo.model.entity.UserEntity;
import com.cursor.demo.model.securityModel.SecurityUser;
import com.cursor.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtDetailsServiceImplementation implements UserDetailsService {

    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user = userService.findByUsername(userName);
        return SecurityUser.getUserDetailsFromUser(user);
    }
}
