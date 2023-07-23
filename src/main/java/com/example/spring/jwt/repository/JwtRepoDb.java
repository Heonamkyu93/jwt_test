package com.example.spring.jwt.repository;

import com.auth0.jwt.JWT;
import com.example.spring.jwt.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JwtRepoDb implements JwtRepository {
    private final DataSource dataSource;
    @PersistenceContext
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
        String sql = "SELECT j FROM member j WHERE j.username = :username";
        return entityManager.createQuery(sql,Member.class)
                .setParameter("username", username)
                .getSingleResult();

    }
}

