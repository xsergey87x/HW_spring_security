package com.cursor.demo.controller;


import com.cursor.demo.model.dto.UserDto;
import com.cursor.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sign-up")
@RequiredArgsConstructor
public class SignUpController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        Long userId = userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }
}
