package com.minh.myshop.factory;

import com.minh.myshop.entity.Role;
import com.minh.myshop.enums.ERole;
import com.minh.myshop.exception.RoleNotFoundException;
import com.minh.myshop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleFactory {
    @Autowired
    RoleRepository roleRepository;

    public Role getInstance(String role) throws RoleNotFoundException {
        switch (role) {
            case "admin" -> {
                return roleRepository.findByName(ERole.ROLE_ADMIN);
            }
            case "customer" -> {
                return roleRepository.findByName(ERole.ROLE_CUSTOMER);
            }
            case "storekeeper" -> {
                return roleRepository.findByName(ERole.ROLE_STOREKEEPER);
            }
            default -> throw new RoleNotFoundException("No role found for " + role);
        }
    }
}
