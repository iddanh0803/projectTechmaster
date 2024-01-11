package com.example.bookinghotel.model.request;

import com.example.bookinghotel.entity.Role;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpsertUserRequest {
    private String username;
    private String password;
    private String email;
    private List<Integer> roles;
}
