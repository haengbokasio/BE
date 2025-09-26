package com.goorm.haengbokasio.controller.match;

import com.goorm.haengbokasio.entity.Matching;
import com.goorm.haengbokasio.entity.Menti;
import com.goorm.haengbokasio.entity.Mentor;
import com.goorm.haengbokasio.service.MatchingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/matching")
@RequiredArgsConstructor
@Tag(name = "Matching", description = "matching 관리 API")
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
            @RequestParam Long mentorKakaoId,
            @RequestParam Long mentiKakaoId) {

        Matching matching = matchingService.createMatching(mentorKakaoId, mentiKakaoId);
        return ResponseEntity.ok(matching);
    }

    @GetMapping("/menti/{mentor_kakaoId}")
    @Operation(summary = "멘토 기준 매칭된 멘티 조회", description = "멘토 카카오ID와 상태값에 따라 연결중인 멘티 리스트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "멘토를 찾을 수 없음")
    })
    public ResponseEntity<List<Menti>> getMentisBySts(
            @PathVariable("mentor_kakaoId") Long mentorKakaoId,
            @RequestParam(required = false) List<String> status) {

        List<Menti> mentiList = matchingService.getMentisBySts(mentorKakaoId, status);
        return ResponseEntity.ok(mentiList);
    }

    @GetMapping("/mento/{menti_kakaoId}")
    @Operation(summary = "멘티 기준 매칭된 멘토 조회", description = "멘티 ID와 상태값에 따라 연결중인 멘토 리스트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "멘토를 찾을 수 없음")
    })
    public ResponseEntity<List<Mentor>> getMentorsBySts(
            @PathVariable("menti_kakaoId") Long mentiKakaoId,
            @RequestParam(required = false) List<String> status) {

        List<Mentor> mentorList = matchingService.getMentorsBySts(mentiKakaoId, status);
        return ResponseEntity.ok(mentorList);
    }

    @PutMapping("/approve")
    @Operation(summary = "요청 승인", description = "멘티의 매칭 요청을 승인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "승인 성공"),
            @ApiResponse(responseCode = "404", description = "대기중인 매칭을 찾을 수 없음"),
            @ApiResponse(responseCode = "400", description = "승인할 수 없는 상태")
    })
    public ResponseEntity<Matching> approveMentiRequest(
            @RequestParam Long mentiKakaoId,
            @RequestParam Long mentorKakaoId) {

        Matching approvedMatching = matchingService.approveMentiRequest(mentorKakaoId, mentiKakaoId);
        return ResponseEntity.ok(approvedMatching);
    }

    @PutMapping("/reject")
    @Operation(summary = "요청 거절", description = "멘티의 매칭 요청을 거절")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "거절 성공"),
            @ApiResponse(responseCode = "404", description = "대기중인 매칭을 찾을 수 없음"),
            @ApiResponse(responseCode = "400", description = "거절할 수 없는 상태")
    })
    public ResponseEntity<String> rejectMentiRequest(
            @RequestParam Long mentiKakaoId,
            @RequestParam Long mentorKakaoId) {

        matchingService.rejectMentiRequest(mentorKakaoId, mentiKakaoId);
        return ResponseEntity.ok("멘티의 매칭 요청이 거절되었습니다.");
    }

    @GetMapping("/all/mentor/{kakaoId}")
    @Operation(
            summary = "멘토의 모든 매칭 조회",
            description = "특정 멘토의 카카오 ID로 해당 멘토와 관련된 모든 매칭 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "멘토를 찾을 수 없음")
    })
    public ResponseEntity<List<Matching>> getMatchingsByMentor(
            @Parameter(description = "멘토의 카카오 ID", required = true)
            @PathVariable Long kakaoId) {
        List<Matching> matchings = matchingService.getMatchingsByMentorKakaoId(kakaoId);
        return ResponseEntity.ok(matchings);
    }

    @GetMapping("/all/menti/{kakaoId}")
    @Operation(
            summary = "멘티의 모든 매칭 조회",
            description = "특정 멘티의 카카오 ID로 해당 멘티와 관련된 모든 매칭 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "멘티를 찾을 수 없음")
    })
    public ResponseEntity<List<Matching>> getMatchingsByMenti(
            @Parameter(description = "멘티의 카카오 ID", required = true)
            @PathVariable Long kakaoId) {
        List<Matching> matchings = matchingService.getMatchingsByMentiKakaoId(kakaoId);
        return ResponseEntity.ok(matchings);
    }
}