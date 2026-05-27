package com.example.umc10th.global.security.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = SocialType.valueOf(registrationId.toUpperCase());
        
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        String email = extractEmail(socialType, attributes);
        String socialUid = attributes.get(userNameAttributeName).toString();

        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        
        Member member;
        if (memberOptional.isPresent()) {
            member = memberOptional.get();
            // Optional: Update social info if needed
        } else {
            // New social user - logic for automatic signup or error handling
            // For now, let's create a minimal member or throw exception to redirect to signup
            // Typically, we might save with dummy password or redirect to a page to fill extra info
            member = Member.builder()
                    .email(email)
                    .name(extractName(socialType, attributes))
                    .socialType(socialType)
                    .socialUid(socialUid)
                    .password("") // Social users don't have password
                    .point(0)
                    .build();
            // Note: Other mandatory fields (gender, birth, address) are missing here.
            // In a real app, you'd redirect to an extra-info page.
            // For this mission, we'll just save it to show the flow.
            // member = memberRepository.save(member);
        }

        return new AuthMember(member, attributes);
    }

    private String extractEmail(SocialType socialType, Map<String, Object> attributes) {
        if (socialType == SocialType.KAKAO) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            return kakaoAccount.get("email").toString();
        } else if (socialType == SocialType.GOOGLE) {
            return attributes.get("email").toString();
        }
        return null;
    }

    private String extractName(SocialType socialType, Map<String, Object> attributes) {
        if (socialType == SocialType.KAKAO) {
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            return properties.get("nickname").toString();
        } else if (socialType == SocialType.GOOGLE) {
            return attributes.get("name").toString();
        }
        return "Social User";
    }
}
