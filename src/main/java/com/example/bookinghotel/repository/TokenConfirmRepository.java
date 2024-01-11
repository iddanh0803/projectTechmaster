package com.example.bookinghotel.repository;

import com.example.bookinghotel.entity.TokenConfirm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenConfirmRepository extends JpaRepository<TokenConfirm, Integer> {
    Optional<TokenConfirm> findByToken(String token);
}
