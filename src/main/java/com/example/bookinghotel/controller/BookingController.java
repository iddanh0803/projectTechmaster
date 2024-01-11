package com.example.bookinghotel.controller;

import com.example.bookinghotel.entity.Booking;
import com.example.bookinghotel.service.BookingService;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String getBookingPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                 Model model){
        Page<Booking> pageData = bookingService.findAll(page,pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("page", page);
        return "admin/booking/index";
    }
    @GetMapping("/create")
    public String getBookingCreatePage(){
        return "admin/booking/create";
    }
}
