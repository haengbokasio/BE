package com.goorm.haengbokasio.repository;

import com.goorm.haengbokasio.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    @Query("SELECT m FROM Matching m WHERE m.mentor.kakaoId = :mentorKakaoId AND m.sts = :sts")
    List<Matching> findByMentorKakaoIdAndSts(@Param("mentorKakaoId") Long mentorKakaoId, @Param("sts") String sts);

    @Query("SELECT m FROM Matching m WHERE m.menti.kakaoId = :mentiKakaoId AND m.sts = :sts")
    List<Matching> findByMentiKakaoIdAndSts(@Param("mentiKakaoId") Long mentiKakaoId, @Param("sts") String sts);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Matching m WHERE m.mentor.kakaoId = :mentorKakaoId AND m.menti.kakaoId = :mentiKakaoId")
    boolean existsByMentorKakaoIdAndMentiKakaoId(@Param("mentorKakaoId") Long mentorKakaoId, @Param("mentiKakaoId") Long mentiKakaoId);

    @Query("SELECT m FROM Matching m WHERE m.mentor.kakaoId = :mentorKakaoId")
    List<Matching> findByMentorKakaoId(@Param("mentorKakaoId") Long mentorKakaoId);

    @Query("SELECT m FROM Matching m WHERE m.menti.kakaoId = :mentiKakaoId")
    List<Matching> findByMentiKakaoId(@Param("mentiKakaoId") Long mentiKakaoId);

    @Query("SELECT m FROM Matching m WHERE m.mentor.kakaoId = :mentorKakaoId AND m.menti.kakaoId = :mentiKakaoId AND m.sts = :sts")
    Optional<Matching> findByMentorKakaoIdAndMentiKakaoIdAndSts(@Param("mentorKakaoId") Long mentorKakaoId, @Param("mentiKakaoId") Long mentiKakaoId, @Param("sts") String sts);

    @Query("SELECT m FROM Matching m WHERE m.mentor.kakaoId = :mentorKakaoId AND m.sts IN :stsList")
    List<Matching> findByMentorKakaoIdAndStsIn(@Param("mentorKakaoId") Long mentorKakaoId, @Param("stsList") List<String> stsList);

    @Query("SELECT m FROM Matching m WHERE m.menti.kakaoId = :mentiKakaoId AND m.sts IN :stsList")
    List<Matching> findByMentiKakaoIdAndStsIn(@Param("mentiKakaoId") Long mentiKakaoId, @Param("stsList") List<String> stsList);
}