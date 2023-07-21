package com.example.spring.jwt.filter;

import com.example.spring.jwt.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;

// 스프링 시큐리티에서 usernamepasswordauthenticationfilter 가 있음
// login 요청해서 post로 username , password 전송하면동작
// 시큐리티 컨피그에 필터를 등록해야지 동작 form disable 했기때문
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager=authenticationManager;
    }
    // 로그인 요청을 하면 로그인 시도를 위해서 실행되는 메소드    
    // 로그인 시도를 하면 UserDetailService 가 호출됨
    // userdetails 를 세션에 담고   세션에 담는이유 권한 관리
    // jwt 토큰으로 응답
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException { //requset.getparameter로 못받는다
        System.out.println("로긴");
        try {
          /*  BufferedReader br =request.getReader();
            String in = null;
            while ((in=br.readLine())!=null){
                System.out.println(in);
            }*/
          //  System.out.println(request.getInputStream().toString());
            System.out.println("안뜨니1");        //프론트에서 어떻게 넘기는지 확인후 처리방법
            ObjectMapper om = new ObjectMapper();  //json 파싱해줌          이건 json으로 넘어올때
            Member member = om.readValue(request.getInputStream(),Member.class);
            System.out.println("안뜨니");
            System.out.println(member.getUsername());
            System.out.println(member.getPassword());
            System.out.println("d====================");

            // 넘어온 정보로 로그인 시도를 하면 된다
            // 로그인시 토큰을 만들어야한다 form 로그인이 알아서 해주지만 disable이니 직접 해야됨
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getUsername(),member.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);  //userdetailservice 의 loaduserbyusername() 메소드가 실행됨
            // 비밀번호는 역시 스프링이 처리해줌
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        System.out.println("==================");
        System.out.println(request.getParameter("username"));
        return super.attemptAuthentication(request, response);
    }
}
