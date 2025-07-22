package com.minh.myshop.controller;

import com.minh.myshop.dto.ApiResponseDto;
import com.minh.myshop.dto.SignInRequestDto;
import com.minh.myshop.dto.SignOutRequestDto;
import com.minh.myshop.dto.SignUpRequestDto;
import com.minh.myshop.exception.RoleNotFoundException;
import com.minh.myshop.exception.UserAlreadyExistsException;
import com.minh.myshop.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponseDto<?>> registerUser(@RequestBody @Valid SignUpRequestDto signUpRequestDto)
            throws UserAlreadyExistsException, RoleNotFoundException {
        return authService.signUp(signUpRequestDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponseDto<?>> signInUser(@RequestBody @Valid SignInRequestDto signInRequestDto) {
        return authService.signIn(signInRequestDto);
    }

    // TODO
    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponseDto<?>> signOutUser(@RequestBody SignOutRequestDto signOutRequestDto) {
        return authService.singOut(signOutRequestDto);
    }

}
