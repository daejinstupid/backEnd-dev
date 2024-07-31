package com.example.demo.constant.handler;

import com.example.demo.constant.enums.CustomResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");
        log.info("exception: "+ exception);

        if(exception == null){
            log.info("exception null 함수 진입");
            response.sendRedirect("/exception/entrypoint/nullToken");
            return;
        }

        if(exception.equals(CustomResponseCode.EXPIRED_JWT.getMessage())){
            response.sendRedirect("/exception/entrypoint/expiredToken");
            return;
        }

        if(exception.equals(CustomResponseCode.BAD_JWT.getMessage())){
            response.sendRedirect("/exception/entrypoint/badToken");
            return;
        }
    }
}
