package com.example.bookinghotel.model.request;

import com.example.bookinghotel.entity.RoomType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpsertRoomRequest {
    private String name;
    private String roomCode;
    private Integer roomTypeId;
    private String location;
    private String price;
}
