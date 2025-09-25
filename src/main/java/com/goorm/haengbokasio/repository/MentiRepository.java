package com.goorm.haengbokasio.repository;

import com.goorm.haengbokasio.entity.Menti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MentiRepository extends JpaRepository<Menti, Long> {

    Optional<Menti> findByKakaoId(String kakaoId);
}