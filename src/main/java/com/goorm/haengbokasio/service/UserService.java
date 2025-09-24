package com.goorm.haengbokasio.service;

import com.goorm.haengbokasio.entity.User;
import com.goorm.haengbokasio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 카카오 ID로 사용자 조회
     */
    public User getUserByKakaoId(Long kakaoId) {
        return userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. 카카오 ID: " + kakaoId));
    }
}
