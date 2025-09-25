package com.goorm.haengbokasio.controller.match;

import com.goorm.haengbokasio.entity.Matching;
import com.goorm.haengbokasio.entity.Menti;
import com.goorm.haengbokasio.entity.Mentor;
import com.goorm.haengbokasio.service.MatchingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/matching")
@RequiredArgsConstructor
public class MatchController {

    private final MatchingService matchingService;

    @PostMapping("/create")
    @Operation(summary = "멘토-멘티 매칭", description = "멘토와 멘티를 매칭하여 매칭 테이블에 데이터 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "매칭 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "멘토 또는 멘티를 찾을 수 없음")
    })
    public ResponseEntity<Matching> createMatching(
            @RequestParam Long mentorId,
            @RequestParam Long mentiId) {

        Matching matching = matchingService.createMatching(mentorId, mentiId);
        return ResponseEntity.ok(matching);
    }

    @GetMapping("/menti/{mentorId}")
    @Operation(summary = "멘토와 연결중인 멘티 조회", description = "멘토 ID와 상태값에 따라 연결중인 멘티 리스트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "멘토를 찾을 수 없음")
    })
    public ResponseEntity<List<Menti>> getMentisBySts(
            @PathVariable Long mentorId,
            @RequestParam(required = false) List<String> status) {

        List<Menti> mentiList = matchingService.getMentisBySts(mentorId, status);
        return ResponseEntity.ok(mentiList);
    }

    @GetMapping("/mento/{kakaoId}")
    @Operation(summary = "멘티와 연결중인 멘토 조회", description = "멘티 ID와 상태값에 따라 연결중인 멘토 리스트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "멘토를 찾을 수 없음")
    })
    public ResponseEntity<List<Mentor>> getMentorsBySts(
            @PathVariable Long mentiId,
            @RequestParam(required = false) List<String> status) {

        List<Mentor> mentorList = matchingService.getMentorsBySts(mentiId, status);
        return ResponseEntity.ok(mentorList);
    }

    @PutMapping("/approve/{kakaoId}")
    @Operation(summary = "멘토가 멘티 요청 승인", description = "멘토가 특정 멘티의 매칭 요청을 승인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "승인 성공"),
            @ApiResponse(responseCode = "404", description = "대기중인 매칭을 찾을 수 없음"),
            @ApiResponse(responseCode = "400", description = "승인할 수 없는 상태")
    })
    public ResponseEntity<Matching> approveMentiRequest(
            @PathVariable Long mentiId,
            @RequestParam Long mentorId) {

        Matching approvedMatching = matchingService.approveMentiRequest(mentorId, mentiId);
        return ResponseEntity.ok(approvedMatching);
    }

    @PutMapping("/reject/{kakaoId}")
    @Operation(summary = "멘토가 멘티 요청 거절", description = "멘토가 특정 멘티의 매칭 요청을 거절")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "거절 성공"),
            @ApiResponse(responseCode = "404", description = "대기중인 매칭을 찾을 수 없음"),
            @ApiResponse(responseCode = "400", description = "거절할 수 없는 상태")
    })
    public ResponseEntity<String> rejectMentiRequest(
            @PathVariable Long mentiId,
            @RequestParam Long mentorId) {

        matchingService.rejectMentiRequest(mentorId, mentiId);
        return ResponseEntity.ok("멘티의 매칭 요청이 거절되었습니다.");
    }
}