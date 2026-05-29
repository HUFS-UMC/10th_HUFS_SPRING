package com.example.umc10th.domain.auth.service;

import com.example.umc10th.domain.auth.dto.AuthReqDTO;
import com.example.umc10th.domain.auth.dto.AuthResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 이메일·비밀번호를 검증하고 JWT AccessToken을 발급합니다.
     *
     * <p>보안 주의: 이메일 미존재와 비밀번호 불일치를 같은 에러 코드(MEMBER401)로
     * 처리해 공격자가 계정 존재 여부를 추측하지 못하도록 합니다.
     *
     * @param dto 이메일·비밀번호 로그인 요청 DTO
     * @return AccessToken을 담은 응답 DTO
     * @throws MemberException INVALID_LOGIN_INFO — 이메일 미존재 또는 비밀번호 불일치
     */
    public AuthResDTO.LoginResultDTO login(AuthReqDTO.LoginDTO dto) {
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.INVALID_LOGIN_INFO));

        // BCrypt 비밀번호 검증
        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.INVALID_LOGIN_INFO);
        }

        // 검증 성공 → AccessToken 발급
        String accessToken = jwtUtil.createAccessToken(member.getEmail());

        return AuthResDTO.LoginResultDTO.builder()
                .accessToken(accessToken)
                .build();
    }
}
