package com.goorm.haengbokasio.repository;

import com.goorm.haengbokasio.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {

    Optional<Mentor> findByKakaoId(String kakaoId);

    List<Mentor> findByIndustry(String industry);

    List<Mentor> findByDisabledFalse();
}