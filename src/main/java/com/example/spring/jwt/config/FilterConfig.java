package com.example.spring.jwt.config;

import com.example.spring.jwt.filter.MyFilter1;
import com.example.spring.jwt.filter.MyFilter2;
import com.example.spring.jwt.filter.MyFilter3;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
public class FilterConfig {
//필터를 여기서 순서를 정하고 실행시킬수 있음  그러나 시큐리티필터가 우선순위에 있음 after ,before 둘다

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        };
    }
    @Bean
    public FilterRegistrationBean<MyFilter1> filter1(){
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        System.out.println("필터1");
        return bean;
    }
    @Bean
    public FilterRegistrationBean<MyFilter2> filter2(){
        FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
        bean.addUrlPatterns("/*");
        bean.setOrder(2);
        System.out.println("필터2");    //숫자가 낮을수록 먼저실행 0 부터 음수도 가능하나 헷갈리니 그냥 양수만 사용하자
        return bean;
    }

 /*   @Bean
    public FilterRegistrationBean<MyFilter3> filter3(){
        FilterRegistrationBean<MyFilter3> bean = new FilterRegistrationBean<>(new MyFilter3());
        bean.addUrlPatterns("/*");
        bean.setOrder(3);
        System.out.println("필터3");
        return bean;
    }*/

}
