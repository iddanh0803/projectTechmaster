package com.example.bookinghotel.repository;

import com.example.bookinghotel.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
