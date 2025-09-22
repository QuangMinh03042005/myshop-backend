package com.minh.myshop.dto;

import com.minh.myshop.entity.User;
import com.minh.myshop.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;

    private String address;

    private String nickname;

    private String email;

    private String phone;

    private Gender gender;

    public UserDto(User user) {
        username = user.getUsername();
        address = user.getAddress();
        nickname = user.getNickname();
        email = user.getEmail();
        phone = user.getPhone();
        gender = user.getGender();
    }
}
