package com.example.umc10th.global.config;

import com.example.umc10th.global.security.CustomUserDetailsService;
import com.example.umc10th.global.security.handler.CustomAccessDeniedHandler;
import com.example.umc10th.global.security.handler.CustomAuthenticationEntryPoint;
import com.example.umc10th.global.security.jwt.JwtAuthFilter;
import com.example.umc10th.global.security.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 필터 체인 설정.
 *
 * <p>필터 실행 순서 (핵심 부분):
 * <pre>
 *   ① JwtAuthFilter          ← Authorization 헤더 파싱 후 SecurityContext 저장
 *   ② UsernamePasswordAuthenticationFilter (비활성 상태)
 *   ③ ExceptionTranslationFilter          ← 인증·인가 예외를 핸들러로 위임
 * </pre>
 *
 * <p>주요 변경 사항 (이전 → 현재):
 * <ul>
 *   <li>formLogin: 활성 → <b>비활성화</b> (REST API 방식으로 전환)</li>
 *   <li>SessionCreationPolicy: STATEFUL → <b>STATELESS</b> (JWT 무상태 인증)</li>
 *   <li>JwtAuthFilter: <b>UsernamePasswordAuthenticationFilter 앞에 추가</b></li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;

    /**
     * JwtAuthFilter 빈을 직접 등록합니다.
     *
     * <p>@Component를 붙이지 않는 이유: Spring Boot는 OncePerRequestFilter 구현체에
     * @Component가 있으면 서블릿 필터로 자동 등록합니다. 그렇게 되면 Security 필터 체인과
     * 별개로 이중 실행되어 한 요청에 두 번 동작하는 문제가 생깁니다.
     * 따라서 @Bean으로만 등록하고 addFilterBefore에서 직접 주입합니다.
     */
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, customUserDetailsService, objectMapper);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ── CSRF: REST API는 세션을 사용하지 않으므로 비활성화 ──────────────
            .csrf(AbstractHttpConfigurer::disable)

            // ── 세션: JWT 무상태 인증 → STATELESS ────────────────────────────
            // STATELESS로 설정하면 SecurityContextHolder가 요청 범위로만 유지됩니다.
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // ── Form 로그인: REST API 방식으로 전환하여 비활성화 ─────────────
            // 이전에 있던 /login 폼 로그인을 제거합니다. 로그인은 POST /auth/login으로 처리합니다.
            .formLogin(AbstractHttpConfigurer::disable)

            // ── 인가 규칙 ────────────────────────────────────────────────────
            .authorizeHttpRequests(auth -> auth
                // Swagger UI는 인증 없이 접근 허용
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/api-docs/**",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()
                // 회원가입 · 로그인은 인증 없이 허용
                .requestMatchers(HttpMethod.POST, "/members").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                // 나머지 모든 요청은 인증 필요
                .anyRequest().authenticated()
            )

            // ── JWT 필터 등록 ─────────────────────────────────────────────────
            // UsernamePasswordAuthenticationFilter 앞에 배치해야 합니다.
            // → 먼저 JWT를 파싱하여 SecurityContext에 인증 객체를 저장한 뒤,
            //   뒤따르는 필터들이 이미 인증된 상태로 동작하도록 합니다.
            .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)

            // ── 인증·인가 예외 핸들러 ─────────────────────────────────────────
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint) // 401
                .accessDeniedHandler(accessDeniedHandler)           // 403
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
