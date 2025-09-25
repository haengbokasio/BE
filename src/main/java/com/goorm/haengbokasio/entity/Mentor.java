package com.goorm.haengbokasio.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "mentor")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", nullable = false, unique = true, length = 100)
    private String kakaoId;

    @Column(name = "industry", length = 100)
    private String industry;

    @Column(name = "revenue", precision = 15, scale = 2)
    private BigDecimal revenue;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "disabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean disabled = false;

    public Mentor() {}

    public Mentor(String kakaoId) {
        this.kakaoId = kakaoId;
        this.disabled = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKakaoId() { return kakaoId; }
    public void setKakaoId(String kakaoId) { this.kakaoId = kakaoId; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Boolean getDisabled() { return disabled; }
    public void setDisabled(Boolean disabled) { this.disabled = disabled; }
}