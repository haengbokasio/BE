package com.goorm.haengbokasio.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "matching")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", referencedColumnName = "kakao_id", nullable = false)
    private Mentor mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menti_id", referencedColumnName = "kakao_id", nullable = false)
    private Menti menti;

    @Column(name = "sts")
    private String sts;
}
