package com.example.demo.security.filter;

import com.example.demo.constant.enums.CustomResponseCode;
import com.example.demo.constant.exception.GeneralException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import com.example.demo.security.service.JwtUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;



    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String accessToken = null;
        String authorizationHeader = request.getHeader("Authorization");
        accessToken = jwtUtils.resolveToken(authorizationHeader);

        log.info("accessToken: "+accessToken);


        if(accessToken != null && !accessToken.isEmpty()){
            try{
                jwtUtils.parseToken(accessToken);
                log.info("accessToken이 null 아님"+accessToken);
                if(jwtUtils.validateToken(accessToken)){
                    //인증객체 생성
                    Authentication authentication = jwtUtils.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }catch (ExpiredJwtException e){
                log.info("expired token");
                request.setAttribute("exception", CustomResponseCode.EXPIRED_JWT.getMessage());
            }catch (JwtException e){
                log.info("invalid token");
                request.setAttribute("exception",CustomResponseCode.BAD_JWT.getMessage());
            }

        }else{
            log.info("accessToken이 null");
            filterChain.doFilter(request,response);
            return;
        }
        filterChain.doFilter(request,response);
    }
}
