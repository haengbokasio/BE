package com.goorm.haengbokasio.controller.login;

import com.goorm.haengbokasio.entity.User;
import com.goorm.haengbokasio.jwt.JwtTokenProvider;
import com.goorm.haengbokasio.service.KakaoLoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Collections;

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
    public void callback(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = kakaoLoginService.getAccessTokenFromKakao(code); // 코드 사용해 토큰 받기

        User user = kakaoLoginService.getUserInfo(accessToken); // 토큰으로 사용자 정보 조회 -> 계정 생성

        String jwtToken = jwtTokenProvider.createToken(user.getKakaoId()); // jwt 토큰 생성

        // 쿠키에 토큰과 카카오ID 저장
        Cookie tokenCookie = new Cookie("token", jwtToken);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(tokenCookie);

        Cookie kakaoIdCookie = new Cookie("kakaoId", String.valueOf(user.getKakaoId()));
        kakaoIdCookie.setHttpOnly(false);
        kakaoIdCookie.setSecure(true);
        kakaoIdCookie.setPath("/");
        kakaoIdCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(kakaoIdCookie);

        String origin = request.getHeader("Origin");
        String host = request.getHeader("Host");
        boolean isLocal = (origin != null && origin.contains("localhost")) || (host != null && host.contains("localhost"));

        String redirectUrl;
        if (isLocal) {
            redirectUrl = "http://localhost:3000/data";
        } else {
            redirectUrl = "";
        }

        response.sendRedirect(redirectUrl);
    }
}
