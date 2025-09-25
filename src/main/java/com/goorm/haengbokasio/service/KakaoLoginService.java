package com.goorm.haengbokasio.service;

import com.goorm.haengbokasio.dto.KakaoTokenResponse;
import com.goorm.haengbokasio.dto.KakaoUserInfoResponse;
import com.goorm.haengbokasio.entity.User;
import com.goorm.haengbokasio.repository.UserRepository;
import io.netty.handler.codec.http.HttpHeaderValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class KakaoLoginService {
    private String clientId;
    private final String KAUTH_TOKEN_URL_HOST;
    private final String KAUTH_USER_URL_HOST;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    private final UserRepository userRepository;


    @Autowired
    public KakaoLoginService(@Value("${kakao.client_id}") String clientId, UserRepository userRepository) {
        this.clientId = clientId;
        this.userRepository = userRepository;
        KAUTH_TOKEN_URL_HOST ="https://kauth.kakao.com";
        KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
    }

    public String getAccessTokenFromKakao(String code) {

        KakaoTokenResponse kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirect_uri)
                        .queryParam("code", code)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResponse.class)
                .block();


        return kakaoTokenResponseDto.getAccessToken();
    }

    public User getUserInfo(String accessToken) {

        KakaoUserInfoResponse userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponse.class)
                .block();

        return findOrCreateUser(userInfo);
    }

    public User findOrCreateUser(KakaoUserInfoResponse kakaoUserInfo) {
        Optional<User> userOptional = userRepository.findByKakaoId(kakaoUserInfo.getId());

        if(userOptional.isPresent()) {
            // 기존 사용자 반환
           return userOptional.get();
        } else {
            // 새 사용자 생성
            User newUser = User.builder()
                    .kakaoId(kakaoUserInfo.getId())
                    .build();

            userRepository.save(newUser);
            return newUser;
        }
    }
}
