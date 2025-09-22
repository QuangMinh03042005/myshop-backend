package com.minh.myshop.controller;

import com.minh.myshop.dto.UserDto;
import com.minh.myshop.entity.Role;
import com.minh.myshop.exception.UserIdNotFoundException;
import com.minh.myshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_STOREKEEPER')")
    public ResponseEntity<?> getUser(@PathVariable("userId") Integer userId) throws UserIdNotFoundException {
        return ResponseEntity.ok(new UserDto(userService.getById(userId)));
    }

    @GetMapping("/{userId}/roles")
    public ResponseEntity<?> getUserRoles(@PathVariable(name = "userId") Integer userId) throws UserIdNotFoundException {
        var user = userService.getById(userId);
        return ResponseEntity.ok(user.getRoles());
    }

    @PostMapping("/{userId}/roles")
    public ResponseEntity<?> addUserRole(@PathVariable(name = "userId") Integer userId, @RequestBody Role role) {
        userService.addUserRole(userId, role);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Integer userId, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userId, userDto));
    }
}
