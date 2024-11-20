package com.example.demo.security.config;

import com.example.demo.constant.exception.CustomAccessDeniedHandler;
import com.example.demo.constant.handler.CustomAuthenticationEntryPoint;
import com.example.demo.security.service.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.demo.security.filter.JwtAuthenticationFilter;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    private final JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
//                .formLogin(AbstractHttpConfigurer::disable)
                .cors().and()
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(
                        request -> request
                                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .antMatchers("/user/**").hasAnyRole("USER","MANAGER")
                                .antMatchers(("/manager/**")).hasRole("MANAGER")
                                .antMatchers("/login").permitAll()
                                .antMatchers("/signup").permitAll()
                                .antMatchers("/check/userName/dup/*").permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling((exceptionHandler) -> exceptionHandler
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler()))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(jwtUtils);
    }
   //비빌번호 인코더 설정
   @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //인증 관리자 설정
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
