package com.example.bookinghotel.repository;

import com.example.bookinghotel.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByUser_IdOrderByCreatedAtDesc(Integer id);
}
