package com.example.bookinghotel.controller;

import com.example.bookinghotel.entity.Room;
import com.example.bookinghotel.entity.RoomType;
import com.example.bookinghotel.repository.RoomRepository;
import com.example.bookinghotel.repository.RoomTypeRepository;
import com.example.bookinghotel.service.ImageService;
import com.example.bookinghotel.service.RoomService;
import com.example.bookinghotel.service.RoomTypeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/rooms")
public class RoomController {
    private final RoomService roomService;
    private final RoomTypeService roomTypeService;
    private final ImageService imageService;
    public RoomController(RoomService roomService, RoomTypeService roomTypeService, RoomRepository roomRepository1, ImageService imageService) {
        this.roomService = roomService;
        this.roomTypeService = roomTypeService;
        this.imageService = imageService;
    }
    @GetMapping
    public String getRoomPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        Page<Room> pageData = roomService.findAll(page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "admin/room/index";
    }
    @GetMapping("/create")
    public String getRoomCreatePage(Model model){
        List<RoomType> roomTypeList = roomTypeService.findAll();
        model.addAttribute("roomTypeList", roomTypeList);
        return "admin/room/create";
    }
    @GetMapping("/{id}/detail")
    public String getRoomDetailPage(@PathVariable Integer id,Model model){
        List<RoomType> roomTypeList = roomTypeService.findAll();
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("imageList", imageService.getFilesOfCurrentUser());
        return "admin/room/detail";
    }
}

