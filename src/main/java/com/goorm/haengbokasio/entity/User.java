package com.goorm.haengbokasio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", nullable = false, unique = true, length = 100)
    private Long kakaoId;

    public User() {}

    public User(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKakaoId() {
        return kakaoId;
    }

    public void setKakaoId(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", kakaoId='" + kakaoId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return kakaoId != null && kakaoId.equals(user.kakaoId);
    }

    @Override
    public int hashCode() {
        return kakaoId != null ? kakaoId.hashCode() : 0;
    }
}