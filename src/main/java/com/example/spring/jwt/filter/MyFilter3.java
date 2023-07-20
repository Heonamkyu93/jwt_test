package com.example.spring.jwt.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("filter3");
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse res = (HttpServletResponse)response;
            String headerAuth=req.getHeader("Authorization");
            res.setCharacterEncoding("utf-8");
            // 토큰 : cos 만들었다치고 만약 cos가 아니면 컨트롤러 진입도 못하게  id , pw 가 정상적으로 들어와 로그인이 완료 되면 토큰을 만들어주고 응답
        // 요청할때마다 header 에 Authorization에 value 값으로 토큰을 가져옴 
        // 그때 내가 만든 토큰인지 검증하면 됨 (RSA , HS256)
            if(req.getMethod().equals("POST")){

                System.out.println(headerAuth);
            }
            if(headerAuth.equals("cos")){
                chain.doFilter(req,res);
            }else{
                System.out.println("인증 ㄴㄴ");
                PrintWriter out = res.getWriter();
                out.println("인증안됨");
            }
       //     chain.doFilter(request,response);   //체인에 걸어줘야함
        }
}
