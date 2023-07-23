package com.example.spring.jwt.controller;

import com.example.spring.jwt.domain.Member;
import com.example.spring.jwt.repository.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RestApiController {
    private final JwtRepository jwtRepository;



    @Autowired
    public RestApiController(JwtRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }




    @GetMapping("/home")
    public String home() {
        return "home";
    }
    @PostMapping("/token")
    public @ResponseBody String token() {
        return "<h1>11</h1>";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute Member member) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_ADMIN");
        jwtRepository.memberSave(member);
        return "home";
    }
    @GetMapping("/savego")
    public String savego(){
        return "save";
    }
    @GetMapping("/api/user/te")
    public String user(){
        return "te1";
    }
    @GetMapping("/api/admin/te")
    public @ResponseBody String  admin(){
        return "te2";
    }
    @GetMapping("/api/member/te")
    public String member(){
        return "te3";
    }
}
