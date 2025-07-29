package com.minh.myshop.controller;

import com.minh.myshop.dto.UserDto;
import com.minh.myshop.entity.Role;
import com.minh.myshop.exception.RoleNotFoundException;
import com.minh.myshop.exception.UserIdNotFoundException;
import com.minh.myshop.repository.RoleRepository;
import com.minh.myshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final  RoleRepository roleRepository;

    @GetMapping("/profile/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_STOREKEEPER')")
    public ResponseEntity<?> getProfile(@PathVariable("id") Integer id) throws UserIdNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserDto(userService.getById(id)));
    }

    @GetMapping("/getUserRoles/{userId}")
    public ResponseEntity<?> getUserRoles(@PathVariable(name = "userId") Integer userId) throws UserIdNotFoundException {
        var user = userService.getById(userId);
        return ResponseEntity.ok(user.getRoles());
    }

    @PutMapping("/addUserRole/{userId}")
    public ResponseEntity<?> addUserRole(@PathVariable(name = "userId") Integer userId, @RequestBody Role role) throws UserIdNotFoundException, RoleNotFoundException {
        System.out.println(role);
        var user = userService.getById(userId);
        role = roleRepository.findByName(role.getName());
        var roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userService.save(user);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/profile/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> updateProfile(@PathVariable("id") Integer id, @RequestBody UserDto userDto) throws UserIdNotFoundException {
        System.out.println(userDto);
        var user = userService.getById(id);
        user.loadFromDto(userDto);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
