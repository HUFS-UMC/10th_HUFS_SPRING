package com.springboot.umc10thlea.global.config;

import com.springboot.umc10thlea.global.security.filter.JwtAuthFilter;
import com.springboot.umc10thlea.global.security.handler.CustomAccessDenied;
import com.springboot.umc10thlea.global.security.handler.CustomEntryPoint;
import com.springboot.umc10thlea.global.security.service.CustomUserDetailsService;
import com.springboot.umc10thlea.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity //Spring Security 설정을 활성화시키는 역할
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final String[] allowUris = {
            // Swagger 허용

            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v3/api-docs/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/sign-up").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                // JWT 필터
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                // 예외 상황 핸들러
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDenied())
                        .authenticationEntryPoint(customEntryPoint())
                )

        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAccessDenied customAccessDenied() {
        return new CustomAccessDenied();
    }

    @Bean
    public CustomEntryPoint customEntryPoint() {
        return new CustomEntryPoint();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, customUserDetailsService);
    }

}
