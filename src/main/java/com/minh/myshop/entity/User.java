package com.minh.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minh.myshop.dto.UserDto;
import com.minh.myshop.enums.EGender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Builder
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "address")
    private String address;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "username", unique = true)
    private String username;

    @ToString.Exclude
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EGender gender = EGender.MALE;

    // TODO
//    private String jwtToken;

    @Column(name = "enabled")
    private boolean enabled;

    @CreationTimestamp
    @ToString.Exclude
    @Column(name = "created_at")
    Date createdAt;

    @UpdateTimestamp
    @ToString.Exclude
    @Column(name = "updatedAt")
    Date updatedAt;

    // bảng này nhỏ nên dùng z
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    Shop shop;


    public void loadFromDto(UserDto userDto) {
        username = userDto.getUsername();
        address = userDto.getAddress();
        nickname = userDto.getNickname();
        email = userDto.getEmail();
        phone = userDto.getPhone();
        gender = userDto.getGender();
    }
}
