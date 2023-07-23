package com.example.spring.jwt.filter;
// 시큐리티가 가진 filter 중 BasicAuthenticationFilter가있는데
// 권한이나 인증이 필요한 특정 주소 요청시 위 필터를 타게됨
// 만약 권한 인증이 필요한 주소가 아니면 사용안함


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.spring.jwt.auth.PrinciapalDetails;
import com.example.spring.jwt.domain.Member;
import com.example.spring.jwt.repository.JwtJpa;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private  final JwtJpa jwtJpa;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,JwtJpa jwtJpa) {
        super(authenticationManager);
        this.jwtJpa=jwtJpa;
    }
        
    
    //인증이나 권한이 필요한 주소요청이 있을때 해당 필터를 타게됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    //    super.doFilterInternal(request, response, chain);
        System.out.println("인증이나 권한이 필요한 주소요청시 실행");
        String jwtHeader = request.getHeader("Authorization");
        System.out.println("authorization= "+jwtHeader);


        //header 가 있는지 체크
        if(jwtHeader==null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }
        // jwt 토큰검증을해서 정상적인 사용자인지 확인
        String jtwToken = request.getHeader("Authorization").replace("Bearer ","");
        String username = JWT.require(Algorithm.HMAC512("cos")).build().verify(jtwToken).getClaim("username").asString();

        // 서명이 정상적으로 됨
        if(username != null){
            Member member = jwtJpa.findByUsername(username);
            System.out.println("멤버에서 꺼낸값"+member.getUsername());
            System.out.println("그냥 유저넴"+username);
            System.out.println(member.getRoles());
            // Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어줌
            PrinciapalDetails princiapalDetails = new PrinciapalDetails(member);
            Authentication authentication=new UsernamePasswordAuthenticationToken(princiapalDetails,null,princiapalDetails.getAuthorities());
            
            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request,response);
        }
        
    }
}
