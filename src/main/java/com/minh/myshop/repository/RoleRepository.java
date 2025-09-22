package com.minh.myshop.repository;

import com.minh.myshop.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<com.minh.myshop.entity.Role, Integer> {
    com.minh.myshop.entity.Role findByName(ERole name);
}
