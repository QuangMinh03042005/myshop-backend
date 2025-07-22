package com.minh.myshop.controller;

import com.minh.myshop.dto.UserDto;
import com.minh.myshop.exception.UserIdNotFoundException;
import com.minh.myshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_STOREKEEPER')")
    public ResponseEntity<?> getProfile(@PathVariable("id") Integer id) throws UserIdNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserDto(userService.getUserById(id)));
    }

    @PutMapping("/profile/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> updateProfile(@PathVariable("id") Integer id, @RequestBody UserDto userDto) throws UserIdNotFoundException {
        System.out.println(userDto);
        var user = userService.getUserById(id);
        user.loadFromDto(userDto);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
