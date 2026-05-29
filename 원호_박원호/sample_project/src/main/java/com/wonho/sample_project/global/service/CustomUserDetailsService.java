package com.wonho.sample_project.global.service;

import com.wonho.sample_project.domains.user.entity.User;
import com.wonho.sample_project.domains.user.repository.UserRepository;
import com.wonho.sample_project.global.api.code.GeneralErrorCode;
import com.wonho.sample_project.global.entity.AuthMember;
import com.wonho.sample_project.global.exception.GeneralHttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public AuthMember loadUserByUsername(String username) throws GeneralHttpException {
        User user = userRepository.findUserByName(username)
                .orElseThrow(() -> new GeneralHttpException(GeneralErrorCode.BAD_REQUEST));

        return new AuthMember(user);
    }
}
