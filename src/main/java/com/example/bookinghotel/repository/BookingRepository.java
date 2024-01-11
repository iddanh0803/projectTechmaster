package com.example.bookinghotel.repository;

import com.example.bookinghotel.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Page<Booking> findByStatus(Boolean status, Pageable pageable);
}
