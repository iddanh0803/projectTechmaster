package com.example.bookinghotel.repository;

import com.example.bookinghotel.entity.Room;
import com.example.bookinghotel.entity.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RoomRepository extends JpaRepository<Room, Integer> {
    Page<Room> findRoomByStatus(Boolean status, Pageable pageable);
    List<Room> findRoomByRoomTypeName(String type);

    List<Room> findRoomByLocation(String location);
    List<Room> findRoomsByStatus(Boolean status);

}
