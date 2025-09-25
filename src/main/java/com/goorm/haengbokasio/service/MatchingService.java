package com.goorm.haengbokasio.service;

import com.goorm.haengbokasio.entity.Matching;
import com.goorm.haengbokasio.entity.Menti;
import com.goorm.haengbokasio.entity.Mentor;
import com.goorm.haengbokasio.repository.MatchingRepository;
import com.goorm.haengbokasio.repository.MentiRepository;
import com.goorm.haengbokasio.repository.MentorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final MentorRepository mentorRepository;
    private final MentiRepository mentiRepository;

    /**
     * 멘토-멘티 매칭 생성
     */
    @Transactional
    public Matching createMatching(Long mentorId, Long mentiId) {
        // 이미 매칭이 존재하는지 확인
        if (matchingRepository.existsByMentorIdAndMentiId(mentorId, mentiId)) {
            throw new IllegalStateException("이미 매칭이 존재합니다.");
        }

        // 멘티가 이미 다른 멘토와 활성 매칭이 있는지 확인
        List<Matching> activeMenteeMatchings = matchingRepository.findByMentiIdAndSts(mentiId, "신청완료");
        if (!activeMenteeMatchings.isEmpty()) {
            throw new IllegalStateException("멘티는 이미 다른 멘토와 매칭되어 있습니다.");
        }

        // 멘티가 대기중인 매칭이 있는지 확인
        List<Matching> pendingMenteeMatchings = matchingRepository.findByMentiIdAndSts(mentiId, "대기중");
        if (!pendingMenteeMatchings.isEmpty()) {
            throw new IllegalStateException("멘티는 이미 매칭 대기 중입니다.");
        }

        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new IllegalArgumentException("멘토를 찾을 수 없습니다."));

        Menti menti = mentiRepository.findById(mentiId)
                .orElseThrow(() -> new IllegalArgumentException("멘티를 찾을 수 없습니다."));

        Matching matching = Matching.builder()
                .mentor(mentor)
                .menti(menti)
                .sts("WAITING") // 대기중
                .build();

        return matchingRepository.save(matching);
    }

    /**
     * 멘토 ID와 상태에 따른 멘티 리스트 조회
     */
    /**
     * 멘토 ID와 상태에 따른 멘티 리스트 조회 (상태 옵션)
     */
    public List<Menti> getMentisBySts(Long mentorId, List<String> statusList) {
        List<Matching> matchings;

        if (statusList == null || statusList.isEmpty()) {
            matchings = matchingRepository.findByMentorId(mentorId);
        } else if (statusList.size() == 1) {
            matchings = matchingRepository.findByMentorIdAndSts(mentorId, statusList.get(0));
        } else {
            matchings = matchingRepository.findByMentorIdAndStsIn(mentorId, statusList);
        }

        return matchings.stream()
                .map(Matching::getMenti)
                .collect(Collectors.toList());
    }

    /**
     * 멘티 ID와 상태에 따른 멘토 리스트 조회
     */
    /**
     * 멘티 ID와 상태에 따른 멘토 리스트 조회 (상태 옵션)
     */
    public List<Mentor> getMentorsBySts(Long mentiId, List<String> statusList) {
        List<Matching> matchings;

        if (statusList == null || statusList.isEmpty()) {
            // 상태 조건 없음 - 모든 매칭 조회
            matchings = matchingRepository.findByMentiId(mentiId);
        } else if (statusList.size() == 1) {
            // 단일 상태 조회
            matchings = matchingRepository.findByMentiIdAndSts(mentiId, statusList.get(0));
        } else {
            // 여러 상태 조회 (IN절)
            matchings = matchingRepository.findByMentiIdAndStsIn(mentiId, statusList);
        }

        return matchings.stream()
                .map(Matching::getMentor)
                .collect(Collectors.toList());
    }

    /**
     * 멘토가 멘티 요청 승인 (대기중 → 신청완료)
     */
    @Transactional
    public Matching approveMentiRequest(Long mentorId, Long mentiId) {
        Matching matching = matchingRepository.findByMentorIdAndMentiIdAndSts(mentorId, mentiId, "APPROVED")
                .orElseThrow(() -> new IllegalArgumentException("해당 멘토-멘티의 대기중인 매칭을 찾을 수 없습니다."));

        matching.setSts("APPROVED");
        return matchingRepository.save(matching);
    }

    /**
     * 멘토가 멘티 요청 거절 (매칭 거절)
     */
    @Transactional
    public void rejectMentiRequest(Long mentorId, Long mentiId) {
        Matching matching = matchingRepository.findByMentorIdAndMentiIdAndSts(mentorId, mentiId, "REJECTED")
                .orElseThrow(() -> new IllegalArgumentException("해당 멘토-멘티의 대기중인 매칭을 찾을 수 없습니다."));

        matching.setSts("REJECTED");
        matchingRepository.save(matching);
    }

}
