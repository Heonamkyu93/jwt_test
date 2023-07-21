package com.example.spring.jwt.repository;

import com.example.spring.jwt.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRepository {

    Member findByUsername(String username);

    void memberSave(Member member);
}
