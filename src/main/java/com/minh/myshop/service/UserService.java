package com.minh.myshop.service;

import com.minh.myshop.entity.User;
import com.minh.myshop.exception.UserIdNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getById(Integer id) throws UserIdNotFoundException;

    User getReferrerById(Integer id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void save(User user);
}
