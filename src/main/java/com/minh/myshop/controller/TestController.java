package com.minh.myshop.controller;

import com.minh.myshop.dto.ApiResponseDto;
import com.minh.myshop.enums.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public ResponseEntity<ApiResponseDto<?>> Test() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .message("Securing Spring Boot using Spring Security and JWT")
                        .build());
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<ApiResponseDto<?>> UserDashboard() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .message("Customer dashboard!")
                        .build());
    }

    //    Only users with 'ROLE_ADMIN' role can access this end point'
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseDto<?>> AdminDashboard() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .message("Admin dashboard!")
                        .build());
    }

    @GetMapping("/storekeeper")
    @PreAuthorize("hasRole('ROLE_STOREKEEPER')")
    public ResponseEntity<ApiResponseDto<?>> SuperAdminDashboard() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .message("Storekeeper dashboard!")
                        .build());
    }

    //    Users with 'ROLE_SUPER_ADMIN' or 'ROLE_ADMIN' roles can access this end point'
    @GetMapping("/customer-or-admin")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseDto<?>> AdminOrSuperAdminContent() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .message("Customer or Admin Content!")
                        .build());
    }
}
