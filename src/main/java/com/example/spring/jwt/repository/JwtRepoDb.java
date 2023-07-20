package com.example.spring.jwt.repository;

import com.example.spring.jwt.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JwtRepoDb implements JwtRepository{
    private final DataSource dataSource;
    private final EntityManager entityManager;

    public JwtRepoDb(DataSource dataSource, EntityManager entityManager) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void memberSave(Member member) {
        entityManager.persist(member);
    }

    @Override
    public Member findByUsername(String username) {

    }
}

