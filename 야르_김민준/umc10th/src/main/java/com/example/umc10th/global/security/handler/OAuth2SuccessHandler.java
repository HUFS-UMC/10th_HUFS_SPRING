package com.example.umc10th.global.security.handler;

import com.example.umc10th.global.security.JwtTokenProvider;
import com.example.umc10th.global.security.entity.AuthMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        AuthMember authMember = (AuthMember) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.createToken(authMember.getMember().getEmail());

        // For simplicity, we redirect to a success URL with the token as a query parameter
        // In a real production app, you might use cookies or a specific redirect URI
        String targetUrl = UriComponentsBuilder.fromUriString("/swagger-ui/index.html")
                .queryParam("accessToken", accessToken)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
