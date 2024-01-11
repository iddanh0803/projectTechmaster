package com.example.bookinghotel.rest;

import com.example.bookinghotel.entity.Room;
import com.example.bookinghotel.model.request.UpsertRoomRequest;
import com.example.bookinghotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/rooms")
public class RoomResource {
    @Autowired
    private RoomService roomService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllRoom(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(roomService.findAll(page,pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Integer id){
        return ResponseEntity.ok(roomService.findById(id));
    }

    @GetMapping("/location")
    public ResponseEntity<?> getRoomByLocation(String location){
        return ResponseEntity.ok(roomService.findByLocation(location));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody UpsertRoomRequest request){
        return new ResponseEntity<>(roomService.createRoom(request), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateRoom(@RequestBody UpsertRoomRequest request,@PathVariable Integer id){
        return ResponseEntity.ok(roomService.updateRoom(request,id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Integer id){
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
