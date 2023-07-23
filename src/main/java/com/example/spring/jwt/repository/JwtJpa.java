package com.example.spring.jwt.repository;

import com.example.spring.jwt.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

public interface JwtJpa extends JpaRepository <Member,Long> {
    public Member findByUsername(String username);
}
