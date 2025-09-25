package com.goorm.haengbokasio.repository;

import com.goorm.haengbokasio.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    // 멘토 ID와 상태로 매칭 조회
    List<Matching> findByMentorIdAndSts(Long mentorId, String sts);

    // 멘티 ID와 상태로 매칭 조회
    List<Matching> findByMentiIdAndSts(Long mentiId, String sts);

    // 특정 멘토-멘티 매칭 존재 여부 확인
    boolean existsByMentorIdAndMentiId(Long mentorId, Long mentiId);

    // 멘토 ID로 모든 매칭 조회
    List<Matching>findByMentorId(Long mentorId);

    // 멘티 ID로 모든 매칭 조회
    List<Matching> findByMentiId(Long mentiId);

    // 특정 멘토-멘티와 상태로 매칭 조회
    Optional<Matching> findByMentorIdAndMentiIdAndSts(Long mentorId, Long mentiId, String sts);

    // 멘토 ID와 상태 목록으로 매칭 조회 (IN절)
    List<Matching> findByMentorIdAndStsIn(Long mentorId, List<String> stsList);

    // 멘티 ID와 상태 목록으로 매칭 조회 (IN절)
    List<Matching> findByMentiIdAndStsIn(Long mentiId, List<String> stsList);
}