package com.example.spring.jwt.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class MyFilter2 implements Filter {


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("filter2");
        chain.doFilter(request,response);
    }
}
