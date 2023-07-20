package com.example.spring.jwt.config;

import com.example.spring.jwt.filter.MyFilter1;
import com.example.spring.jwt.filter.MyFilter3;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@Data
public class SecurityConifg {
    private final CorsFilter corsFilter;

    public SecurityConifg(CorsFilter corsFilter) {
        this.corsFilter = corsFilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class);//시큐리티가 제공하는 필터중 앞이나 뒤에 걸어줘야함 before, after 가장먼저 실행하고싶으면 시큐리티 필터중 가장 앞에 있는걸 알아내서 before로 걸어주면 된다
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  //세션을 사용안함
                .formLogin(AbstractHttpConfigurer::disable)    //일반 로그인처리 사용안함
                .httpBasic(AbstractHttpConfigurer::disable)     // 일반 아이디 비밀번호 들고다니면서 사용자 확인하는거 사용안함
                .addFilter(corsFilter)      //  인증이 있을경우 시큐리티 필터에 등록해줘야함  corsFilter 에가면 여러가지 설정을 다허용하는데 그래서 보안 cors정책을 안받는거
                //   컨트롤러에 @CrossOrigin 어노태이션을 걸면 인증이 필요한 요청은 다 거부된다 인증이 필요하지않은 요청만 있을경우 사용
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/api/user/**").authenticated()
                        .requestMatchers("/api/member/**").access("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER','ROLE_GUEST')")
                        .requestMatchers("/api/family/**").access("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
                        .requestMatchers("/api/manager/**").access("hasAnyRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")    // admin 이나 manager 권한이 있는 사람만 접속가능
                        .requestMatchers("/api/admin/**").access("hasAnyRole('ROLE_ADMIN')")
                        .anyRequest().permitAll()
                );
        return http.build();
    }

}
