package com.example.bookinghotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String room_code;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
    private String location;
    private String image;
    private Integer adult;
    private Integer kid;
    private Double price;
    private String description;
    private boolean status;
}
