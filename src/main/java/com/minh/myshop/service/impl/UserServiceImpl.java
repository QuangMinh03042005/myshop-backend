package com.minh.myshop.service.impl;

import com.minh.myshop.entity.User;
import com.minh.myshop.exception.NotFoundException;
import com.minh.myshop.exception.UserIdNotFoundException;
import com.minh.myshop.repository.UserRepository;
import com.minh.myshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserById(Integer id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("user not found with id = " + id));
    }

    @Override
    public User getReferrerById(Integer id) {
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
    public void save(User user) {
        userRepository.save(user);
    }
}
