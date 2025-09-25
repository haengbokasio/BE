package com.goorm.haengbokasio.controller.user;

import com.goorm.haengbokasio.dto.MentiRegisterDto;
import com.goorm.haengbokasio.dto.MentorRegisterDto;
import com.goorm.haengbokasio.service.MentiService;
import com.goorm.haengbokasio.service.MentorService;
import com.goorm.haengbokasio.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "user 관리 API")
public class UserController {

    private final UserService userService;

    private final MentorService mentorService;
    private final MentiService mentiService;

    /*
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
     */

    @GetMapping("/mentis")
    @Operation(summary = "전체 멘티 조회", description = "등록된 모든 멘티 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멘티 목록 조회 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<?> getAllMentis() {
        try {
            List<?> mentis = mentiService.getAllMentis();
            return ResponseEntity.ok(mentis);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mentisOrderByMonthAvg")
    @Operation(summary = "월매출 순 멘토 리스트 조회", description = "월매출 순 멘토 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멘토 목록 조회 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<?> mentisOrderByMonthAvg() {
        try {
            List<?> mentis = mentorService.mentisOrderByMonthAvg();
            return ResponseEntity.ok(mentis);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("mypage/{kakaoId}")
    @Operation(summary = "카카오 ID로 조회", description = "사용자 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<?> getUserInfo(@PathVariable Long kakaoId) {
        Object userInfo = userService.getUserInfo(kakaoId);
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/mentor/register/{kakaoId}")
    @Operation(summary = "멘토 정보 등록", description = "신규 멘토의 상세 정보를 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "멘토 등록 성공 (ID 반환)"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 멘토 정보 (충돌)"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Long> registerMentor(@RequestBody MentorRegisterDto registerDto) {

        try {
            Long mentorId = mentorService.registerMentor(registerDto);
            return new ResponseEntity<>(mentorId, HttpStatus.CREATED); // 멘토 id 반환
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/menti/register/{kakaoId}")
    @Operation(summary = "멘티 정보 등록", description = "신규 멘티의 상세 정보를 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "멘티 등록 성공 (ID 반환)"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 멘티 정보 (충돌)"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Long> registerMenti(@PathVariable Long kakaoId, @RequestBody MentiRegisterDto registerDto) {

        try {
            Long mentorId = mentiService.registerMentor(registerDto);
            return new ResponseEntity<>(mentorId, HttpStatus.CREATED); // 멘토 id 반환
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
