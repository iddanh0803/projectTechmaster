package com.example.bookinghotel.model.dto;


import com.example.bookinghotel.entity.Role;
import com.example.bookinghotel.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Integer id;
    private String username;
    private String full_name;
    private String address;
    private String phone;
    private String email;
    private String avatar;
    private String date_birth;
    private Integer gender;
    private String nationality;
    private List<Role> roles;
    public UserDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.full_name = user.getFull_name();
        this.address = user.getAddress();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.date_birth = user.getDate_birth();
        this.gender = user.getGender();
        this.nationality = user.getNationality();
        this.roles = user.getRoles();
    }
}
