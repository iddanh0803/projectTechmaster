package com.example.bookinghotel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String booking_code;
    private Date check_in;
    private Date check_out;
    private boolean status;
    private Integer kid;
    private Integer adult;
    private Double total_amount;
    private String payment;
    private String message;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt =LocalDateTime.now();
    }
}
