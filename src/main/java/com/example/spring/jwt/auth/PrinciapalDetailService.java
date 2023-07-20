package com.example.spring.jwt.auth;

import com.example.spring.jwt.domain.Member;
import com.example.spring.jwt.repository.JwtRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PrinciapalDetailService implements UserDetailsService {

    private final JwtRepository jwtRepository;

    public PrinciapalDetailService(JwtRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member=jwtRepository.findByUsername(username);
        return null;
    }
}
