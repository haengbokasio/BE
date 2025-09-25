package com.goorm.haengbokasio.service;

import com.goorm.haengbokasio.entity.Menti;
import com.goorm.haengbokasio.entity.Mentor;
import com.goorm.haengbokasio.entity.User;
import com.goorm.haengbokasio.repository.MentiRepository;
import com.goorm.haengbokasio.repository.MentorRepository;
import com.goorm.haengbokasio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;
    private final MentiRepository mentiRepository;

    /**
     * 카카오 ID로 사용자 조회
     */
    public User getUserByKakaoId(Long kakaoId) {
        return userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. 카카오 ID: " + kakaoId));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 마이페이지 조회
     */
    public Object getUserInfo(Long kakaoId) {
        // 먼저 user 테이블에서 해당 kakaoId가 존재하는지 확인
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. 카카오 ID: " + kakaoId));

        // mentor 테이블에서 조회
        Optional<Mentor> mentor = mentorRepository.findByKakaoId(kakaoId);
        if (mentor.isPresent()) {
            return mentor.get();
        }

        // menti 테이블에서 조회
        Optional<Menti> menti = mentiRepository.findByKakaoId(kakaoId);
        if (menti.isPresent()) {
            return menti.get();
        }

        // 둘 다 없으면 기본 User 정보만 반환 또는 예외 처리
        throw new RuntimeException("멘토/멘티 정보를 찾을 수 없습니다. 카카오 ID: " + kakaoId);
    }
}
