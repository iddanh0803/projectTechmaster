package com.example.bookinghotel.repository;

import com.example.bookinghotel.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {
}
