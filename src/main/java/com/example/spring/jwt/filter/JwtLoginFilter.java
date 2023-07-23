package com.example.spring.jwt.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.spring.jwt.auth.PrinciapalDetails;
import com.example.spring.jwt.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

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
                    //프론트에서 어떻게 넘기는지 확인후 처리방법
            ObjectMapper om = new ObjectMapper();  //json 파싱해줌          이건 json으로 넘어올때
            Member member = om.readValue(request.getInputStream(),Member.class);


            // 넘어온 정보로 로그인 시도를 하면 된다
            // 로그인시 토큰을 만들어야한다 form 로그인이 알아서 해주지만 disable이니 직접 해야됨
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getUsername(),member.getPassword());

            // authentication 가 제대로 출력된다는건 db의 값과 일치한다는뜻
            Authentication authentication = authenticationManager.authenticate(authenticationToken);  //userdetailservice 의 loaduserbyusername() 메소드가 실행됨
            // 비밀번호는 역시 스프링이 처리해줌
            PrinciapalDetails princiapalDetails = (PrinciapalDetails) authentication.getPrincipal();
            //authentication 객체가 정상적이란 소리니깐 이게 출력되면 로그인이 된것임
            System.out.println("로그인이되었나?="+princiapalDetails.getMember().getUsername());

            // 여기서 세션에 저장됨  권한관리를 대신해주기때문에 
            // 토큰을 사용하는데 세션을 이용하는 이유
         return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //여기서 토큰을 만들지 않는이유 attemptAuthentication 메소드가 인증이 정상적으로 되면  success 메소드가 실행됨
        //jwt 토큰을 만들어서 request  요청한 사용자에서 토큰을 response해주면됨


    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
            System.out.println("로그인 완료되면 이거실행되는거 맞나?");


            // hash 이건 암호   rsa 공개키
        PrinciapalDetails princiapalDetails = (PrinciapalDetails) authResult.getPrincipal();
        String jwtToken= JWT.create()
                .withSubject("토큰")
                        .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))   //만료시간
                                .withClaim("username",princiapalDetails.getUsername())
                                        .sign(Algorithm.HMAC512("cos"));  //암호

            response.addHeader("Authorization","Bearer "+jwtToken);
    }
}
