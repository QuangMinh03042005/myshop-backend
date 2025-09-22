package com.minh.myshop.service.impl;

import com.minh.myshop.dto.UserDto;
import com.minh.myshop.entity.Role;
import com.minh.myshop.entity.User;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.repository.RoleRepository;
import com.minh.myshop.repository.UserRepository;
import com.minh.myshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User getById(Integer id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("user not found with id = " + id));
    }

    @Override
    public User getReferrer(Integer id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto addUser(User user) {
        return new UserDto(userRepository.save(user));
    }

    @Override
    public void addUserRole(Integer id, Role role) {
        var user = this.getById(id);
        role = roleRepository.findByName(role.getName());
        var roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        this.addUser(user);
    }

    @Override
    public UserDto updateUser(Integer id, UserDto userDto) {
        var user = this.getById(id);
        user.loadFromDto(userDto);
        return this.addUser(user);
    }
}
