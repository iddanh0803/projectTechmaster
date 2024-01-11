package com.example.bookinghotel.service;

import com.example.bookinghotel.entity.RoomType;
import com.example.bookinghotel.repository.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }
    public List<RoomType> findAll(){
        return roomTypeRepository.findAll();
    }
}
