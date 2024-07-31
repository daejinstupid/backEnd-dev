package com.example.demo.security.service;

import com.example.demo.constant.enums.CustomResponseCode;
import com.example.demo.constant.exception.GeneralException;
import com.example.demo.userss.mapper.UsersMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {

    //비밀키(누출되면 안됨)
    private final String secretKey = "secret";

    private final UserDetailsService userDetailsService;

    // JWT 토큰 생성: 사용자 아이디 포함
    public String createToken(String userName) {
        String token = null;
        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ", "JWT"); //토큰의 종류
        builder.setHeaderParam("alg", "HS256"); //암호화 알고리즘 종류
        builder.setExpiration(new Date(new Date().getTime() + 1000*60*60*12)); //토큰의 유효기간
        builder.claim("userName", userName); //토큰에 저장되는 데이터
        builder.signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8)); //비밀키
        token = builder.compact(); //모든 내용을 묶기
        return token;
    }

    //JWT 토큰에서 모든 내용(Claims) 얻기
    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            claims = jws.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    //JWT 토큰에서 사용자 이름 얻기
    public String getUserName(String token) {
        String userName = null;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            userName = claims.get("userName", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
    }

    public Claims parseToken(String token){

        JwtParser parser = Jwts.parser();
        parser.setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8));
        Jws<Claims> jws = parser.parseClaimsJws(token);
        Claims claims = jws.getBody();
        return claims;
    }

    //JWT 토큰 유효성 검사: 만료일자 확인 true - 유효,
    public boolean validateToken(String token) {
        boolean validate = false;
        Claims claims = parseToken(token);

        validate = !claims.getExpiration().before(new Date());



        return validate;
    }

    public Authentication getAuthentication(String token){

        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserName(token));
        if(userDetails == null){
            throw new GeneralException(CustomResponseCode.USER_NOT_FOUND);
        }
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String resolveToken(String token){
        String accessToken="";
        if (token != null) {
            return token.substring("Bearer ".length());
        } else {
            return "";
        }
//        else {
//            return httpServletRequest.getParameter("accessToken");
//        }

    }

}
