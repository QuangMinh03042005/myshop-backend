package com.minh.myshop.service;

import com.minh.myshop.dto.ApiResponseDto;
import com.minh.myshop.dto.SignInRequestDto;
import com.minh.myshop.dto.SignOutRequestDto;
import com.minh.myshop.dto.SignUpRequestDto;
import com.minh.myshop.exception.RoleNotFoundException;
import com.minh.myshop.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> signUp(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException, RoleNotFoundException;

    ResponseEntity<ApiResponseDto<?>> signIn(SignInRequestDto signInRequestDto);

    ResponseEntity<ApiResponseDto<?>> singOut(SignOutRequestDto signOutRequestDto);
}
