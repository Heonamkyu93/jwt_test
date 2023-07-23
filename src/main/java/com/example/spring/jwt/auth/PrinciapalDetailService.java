package com.example.spring.jwt.auth;

import com.example.spring.jwt.domain.Member;
import com.example.spring.jwt.repository.JwtJpa;
import com.example.spring.jwt.repository.JwtRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrinciapalDetailService implements UserDetailsService {

    public PrinciapalDetailService(JwtJpa jwtJpa) {
        this.jwtJpa = jwtJpa;
    }

    private final JwtJpa jwtJpa;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // Member member=jwtRepository.findByUsername(username);
            Member member = jwtJpa.findByUsername(username);
        return new PrinciapalDetails(member);
    }
}
