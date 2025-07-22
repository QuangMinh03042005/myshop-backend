package com.minh.myshop.repository;

import com.minh.myshop.entity.Role;
import com.minh.myshop.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(ERole name);
}
