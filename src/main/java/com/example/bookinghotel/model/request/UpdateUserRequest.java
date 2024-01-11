package com.example.bookinghotel.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateUserRequest {
    private String username;
    private String full_name;
    private String address;
    private String phone;
    private String email;
    private String avatar;
    private String nationality;
    private List<Integer> roles;
}
