package com.example.bookinghotel.model.request;

import com.example.bookinghotel.entity.User;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpsertBookingRequest {
    private String booking_code;
    private Date check_in;
    private Date check_out;
    private Double total_amount;
    private String payment;
    private User user;
}
