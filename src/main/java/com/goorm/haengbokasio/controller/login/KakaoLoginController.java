package com.goorm.haengbokasio.controller.login;

import com.goorm.haengbokasio.entity.User;
import com.goorm.haengbokasio.jwt.JwtTokenProvider;
import com.goorm.haengbokasio.service.KakaoLoginService;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;
    private final JwtTokenProvider jwtTokenProvider;

    public KakaoLoginController(KakaoLoginService kakaoLoginService, JwtTokenProvider jwtTokenProvider) {
        this.kakaoLoginService = kakaoLoginService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Description("카카오 로그인 콜백 url : 코드를 이용해 토큰을 받고, 해당 토큰으로 사용자 정보 조회")
    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) throws IOException {
        String accessToken = kakaoLoginService.getAccessTokenFromKakao(code); // 코드 사용해 토큰 받기

        User user = kakaoLoginService.getUserInfo(accessToken); // 토큰으로 사용자 정보 조회 -> 계정 생성

        String jwtToken = jwtTokenProvider.createToken(user.getKakaoId()); // jwt 토큰 생성

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtToken);
        response.put("kakaoId", user.getKakaoId());

        return ResponseEntity.ok(response);
    }
}
