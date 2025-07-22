package com.minh.myshop.entity;

import com.minh.myshop.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
