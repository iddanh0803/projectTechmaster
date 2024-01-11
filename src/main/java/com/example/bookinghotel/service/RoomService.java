package com.example.bookinghotel.service;

import com.example.bookinghotel.entity.Room;
import com.example.bookinghotel.entity.RoomType;
import com.example.bookinghotel.exception.NotFoundException;
import com.example.bookinghotel.model.request.UpsertRoomRequest;
import com.example.bookinghotel.repository.RoomRepository;
import com.example.bookinghotel.repository.RoomTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    public RoomService(RoomRepository roomRepository, RoomTypeRepository roomTypeRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
    }
    public Page<Room> findAll(Integer page, Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        return roomRepository.findRoomByStatus(true, pageable);
    }
    public List<Room> findByStatus(Boolean status){
        return roomRepository.findRoomsByStatus(status);
    }
    public Room findById(Integer id){
        return roomRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found room with id: "+ id));
    }
    public List<Room> findByLocation(String location){
        return roomRepository.findRoomByLocation(location);
    }
    public Room createRoom(UpsertRoomRequest request){
       Room room = new Room();
       try {
           room.setName(request.getName());
           room.setRoom_code(request.getRoomCode());
           room.setPrice(Double.parseDouble(request.getPrice()) );
           room.setLocation(request.getLocation());
           room.setRoomType(roomTypeRepository.findRoomTypeById(request.getRoomTypeId()));
           roomRepository.save(room);
       } catch (Exception e) {
           e.printStackTrace();
       }
        return room;
    }
    public Room updateRoom(UpsertRoomRequest request,Integer id){
        Room room = roomRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found room with id: " + id));
        room.setName(request.getName());
        room.setRoom_code(request.getRoomCode());
        room.setPrice(Double.parseDouble(request.getPrice()) );
        room.setLocation(request.getLocation());
        room.setRoomType(roomTypeRepository.findRoomTypeById(request.getRoomTypeId()));
//        room.setAdult(request.getAdult());
//        room.setKid(request.getKid());
//        room.setDescription(request.getDescription());
        return roomRepository.save(room);
    }
    public void deleteRoom(Integer id){
        Room room = roomRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found room with id: "+id));
        roomRepository.delete(room);
    }
}
