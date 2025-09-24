package com.goorm.haengbokasio.controller.user;

import com.goorm.haengbokasio.entity.User;
import com.goorm.haengbokasio.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "user 관리 API")
public class UserController {

    private final UserService userService;

    @GetMapping("/kakao/{kakaoId}")
    @Operation(summary = "카카오 ID로 조회", description = "카카오 ID를 통해 사용자를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<User> getUserByKakaoId(@PathVariable Long kakaoId) {
        User user = userService.getUserByKakaoId(kakaoId);
        return ResponseEntity.ok(user);
    }
}
