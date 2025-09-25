package com.goorm.haengbokasio.controller.login;

import com.goorm.haengbokasio.entity.User;
import com.goorm.haengbokasio.jwt.JwtTokenProvider;
import com.goorm.haengbokasio.service.KakaoLoginService;
import com.goorm.haengbokasio.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Tag(name = "Kakao", description = "Kakao user 관리 API")

public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Description("카카오 로그인 콜백 url : 코드를 이용해 토큰을 받고, 해당 토큰으로 사용자 정보 조회")
    @GetMapping("/callback")
    public void callback(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = kakaoLoginService.getAccessTokenFromKakao(code); // 코드 사용해 토큰 받기

        User user = kakaoLoginService.getUserInfo(accessToken); // 토큰으로 사용자 정보 조회 -> 계정 생성

        String jwtToken = jwtTokenProvider.createToken(user.getKakaoId()); // jwt 토큰 생성

        // 쿠키에 토큰과 카카오ID 저장
        Cookie tokenCookie = new Cookie("token", jwtToken);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(tokenCookie);

        Cookie kakaoIdCookie = new Cookie("kakaoId", String.valueOf(user.getKakaoId()));
        kakaoIdCookie.setHttpOnly(false);
        kakaoIdCookie.setPath("/");
        kakaoIdCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(kakaoIdCookie);

        // String origin = request.getHeader("Origin");
        // String host = request.getHeader("Host");

        String redirectUrl = "https://goormthon-6.goorm.training/onboarding?kakaoId="+String.valueOf(user.getKakaoId());

        response.sendRedirect(redirectUrl);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser() {
        // 랜덤 카카오ID 생성
        Random random = new Random();
        Long randomKakaoId = 1000000000L + (long)(random.nextDouble() * 9000000000L);


        // User 생성 및 저장
        User user = User.builder()
                .kakaoId(randomKakaoId)
                .build();

        User savedUser = userService.saveUser(user);

        return ResponseEntity.ok(savedUser);
    }
}
