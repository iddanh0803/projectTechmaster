package com.example.bookinghotel.controller;

import com.example.bookinghotel.entity.Blog;
import com.example.bookinghotel.entity.Room;
import com.example.bookinghotel.service.BlogService;
import com.example.bookinghotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private RoomService roomService;
    @GetMapping("/home")
    public String getHome(){
        return "web/main";
    }
    @GetMapping("/property")
    public String getProperty(Model model){
        model.addAttribute("rooms",roomService.findByStatus(true));
        return "web/property";
    }
    @GetMapping("/{id}/property-detail")
    public String getPropertyDetail(@PathVariable Integer id, Model model){
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        return "web/property-detail";
    }
    @GetMapping("/blogs")
    public String getBlogs(Model model){
        model.addAttribute("blogs", blogService.findByStatusOrderByCreatedAtDesc(true));
        return "web/blog";
    }
    @GetMapping("/{id}/blog-detail")
    public String getBlogDetail(@PathVariable Integer id,Model model){
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return "web/blog-detail";
    }
}
