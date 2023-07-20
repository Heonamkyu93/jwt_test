package com.example.spring.jwt.repository;

import com.example.spring.jwt.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRepository {
    void memberSave(Member member);

    Member findByUsername(String username);
}
