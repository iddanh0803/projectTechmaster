package com.example.bookinghotel.repository;

import com.example.bookinghotel.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
