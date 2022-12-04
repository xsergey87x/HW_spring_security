package com.cursor.demo.controller;

import com.cursor.demo.model.dto.AuthenticationRequestDto;
import com.cursor.demo.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class SignInController {
    @Value("Authorization")
    private String authorizationHeader;
    private final SecurityService securityService;

    @PostMapping("/login")
    public void authenticate(@RequestBody AuthenticationRequestDto authRequestDto,HttpServletResponse response)
    {
        String token = securityService.getAuthentication(authRequestDto);
        response.setHeader(authorizationHeader, token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
