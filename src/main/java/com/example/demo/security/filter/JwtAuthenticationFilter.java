package com.example.demo.security.filter;

import com.example.demo.constant.enums.CustomResponseCode;
import com.example.demo.constant.exception.GeneralException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.demo.security.service.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String accessToken = null;
        String authorizationHeader = request.getHeader("Authorization");

        // 로그 추가: Authorization 헤더 출력
        log.info("Authorization Header: " + authorizationHeader);

        // JWT 토큰 추출
        accessToken = jwtUtils.resolveToken(authorizationHeader);

        // 로그 추가: 추출한 accessToken 출력
        log.info("Extracted accessToken: " + accessToken);

        if (accessToken != null && !accessToken.isEmpty()) {
            try {
                // 토큰 파싱 (0.11.2 버전에 맞게 처리)
                Claims claims = jwtUtils.getClaims(accessToken);
                log.info("Token parsed successfully: " + accessToken);

                // 토큰 유효성 검사
                if (jwtUtils.validateToken(accessToken)) {
                    // 인증 객체 생성
                    Authentication authentication = jwtUtils.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Token is valid. Authentication set.");
                } else {
                    log.info("Token is invalid.");
                }
            } catch (ExpiredJwtException e) {
                log.error("Expired token exception: ", e);
                request.setAttribute("exception", CustomResponseCode.EXPIRED_JWT.getMessage());
            } catch (JwtException e) {
                log.error("Invalid token exception: ", e);
                request.setAttribute("exception", CustomResponseCode.BAD_JWT.getMessage());
            }
        } else {
            log.info("No accessToken found.");
        }

        // 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }
}