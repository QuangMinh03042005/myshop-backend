package com.minh.myshop.service;

import com.minh.myshop.dto.UserDto;
import com.minh.myshop.entity.Role;
import com.minh.myshop.entity.User;
import com.minh.myshop.exception.UserIdNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getById(Integer id) throws UserIdNotFoundException;

    User getReferrer(Integer id);

    UserDto addUser(User user);

    UserDto updateUser(Integer id, UserDto userDto);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void addUserRole(Integer id, Role role);
}
