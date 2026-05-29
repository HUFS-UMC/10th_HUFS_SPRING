package com.example.umc10th.global.security.jwt;

import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * HTTP 요청당 한 번만 실행되는 JWT 인증 필터입니다.
 *
 * <p>처리 흐름:
 * <ol>
 *   <li>Authorization 헤더에서 "Bearer " 접두사를 제거하고 토큰 추출</li>
 *   <li>JwtUtil로 서명·만료 검증</li>
 *   <li>토큰이 유효하면 CustomUserDetailsService로 UserDetails를 로드하고
 *       SecurityContextHolder에 Authentication 저장</li>
 *   <li>예외 발생 시 ObjectMapper로 JSON 에러 응답 반환 (필터 체인 중단)</li>
 * </ol>
 *
 * <p><b>주의:</b> 이 클래스는 {@code @Component}를 붙이지 않습니다.
 * Spring이 서블릿 필터로 자동 등록하면 Security 필터 체인과 별개로 이중 실행됩니다.
 * SecurityConfig에서 {@code addFilterBefore}로 직접 등록합니다.
 */
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = extractToken(request);

            // 토큰이 존재하고 유효한 경우에만 인증 처리
            if (token != null && jwtUtil.isValid(token)) {
                String email = jwtUtil.getEmail(token);

                // DB에서 UserDetails(AuthMember) 로드
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                // SecurityContextHolder에 저장할 Authentication 객체 생성
                // credentials는 null — 이미 JWT로 검증 완료
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                // 요청 IP 등 부가 정보 세팅
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContextHolder에 등록 → @AuthenticationPrincipal 사용 가능
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // 인증 여부와 무관하게 다음 필터로 전달
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // 토큰 파싱 중 예외(만료·위변조 등) 발생 시 JSON 에러 응답 반환
            sendErrorResponse(response, "COMMON401_1", "인증에 실패하였습니다.");
        }
    }

    /**
     * Authorization 헤더에서 "Bearer " 접두사를 제거하고 순수 토큰을 반환합니다.
     *
     * @return JWT 문자열, 헤더가 없거나 형식이 맞지 않으면 null
     */
    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    /**
     * 401 Unauthorized JSON 응답을 직접 작성합니다.
     * 필터 체인을 중단하고 응답을 종료합니다.
     */
    private void sendErrorResponse(HttpServletResponse response, String code, String message)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ApiResponse<Object> errorResponse = ApiResponse.onFailure(code, message, null);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
