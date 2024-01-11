package com.example.bookinghotel.repository;

import com.example.bookinghotel.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
    Optional<RoomType> findByName(String name);

    RoomType findRoomTypeById(Integer id);
}
