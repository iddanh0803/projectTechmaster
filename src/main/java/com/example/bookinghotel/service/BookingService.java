package com.example.bookinghotel.service;

import com.example.bookinghotel.entity.Blog;
import com.example.bookinghotel.entity.Booking;
import com.example.bookinghotel.exception.NotFoundException;
import com.example.bookinghotel.model.request.UpdateUserRequest;
import com.example.bookinghotel.model.request.UpsertBookingRequest;
import com.example.bookinghotel.repository.BookingRepository;
import com.example.bookinghotel.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    public Page<Booking> findAll(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return bookingRepository.findAll(pageable);
    }

    public Booking getBookingById(Integer id){
        return bookingRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found booking with id: " + id));
    }

    public Booking createBooking(UpsertBookingRequest request){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Booking booking = new Booking();
        booking.setBooking_code(request.getBooking_code());
        booking.setCheck_in(request.getCheck_in());
        booking.setCheck_out(request.getCheck_out());
        booking.setTotal_amount(request.getTotal_amount());
        booking.setPayment(request.getPayment());
        booking.setUser(customUserDetails.getUser());
        return bookingRepository.save(booking);
    }

//    public Booking updateBooking(UpsertBookingRequest request, Integer id){
//        Booking booking = bookingRepository.findById(id)
//                .orElseThrow(()-> new NotFoundException("Not found booking with id: " + id));
//        booking.setBooking_code(request.getBooking_code());
//        booking.setCheck_in(request.getCheck_in());
//        booking.setCheck_out(request.getCheck_out());
//        booking.setTotal_amount(request.getTotal_amount());
//        booking.setPayment(request.getPayment());
//
//    }
}
