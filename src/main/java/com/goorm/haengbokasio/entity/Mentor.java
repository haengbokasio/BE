package com.goorm.haengbokasio.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goorm.haengbokasio.enums.CustomerAgeGroup;
import com.goorm.haengbokasio.enums.MarketingMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "mentor")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", nullable = false, unique = true, length = 100)
    private Long kakaoId;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "business_type", nullable = false, length = 50)
    private String businessType;

    @Column(name = "business_detail", length = 100)
    private String businessDetail;

    @Column(name = "operation_period")
    private Integer operationPeriod; // 운영 기간(년차)

    @Column(name = "month_avg_revenue")
    private Integer monthAvgRevenue; // 월 평균 순 이익

    @Column(name = "week_avg_daily_revenue")
    private Integer weekAvgDailyRevenue; // 평균 일매출

    @Column(name = "business_address")
    private String businessAddress; // 매장 주소 or 원하는 상권 위

    @Lob
    @Column(name = "main_product_service", columnDefinition = "TEXT")
    private String mainProductService; // 대표 상품, 서비

    @Enumerated(EnumType.STRING)
    @Column(name = "target_customer")
    private CustomerAgeGroup targetCustomer; // 주 고객 - enum

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_acquisition_method")
    private CustomerAgeGroup customerAcquisitionMethod; // 타겟층 - enum

    @Enumerated(EnumType.STRING)
    @Column(name = "marketing_method")
    private MarketingMethod marketingMethod; // 홍보 방법 - enum

    @Lob
    @Column(name = "operation_method", columnDefinition = "TEXT")
    private String operationMethod; // 운영 방법

    @Lob
    @Column(name = "supply_source", columnDefinition = "TEXT")
    private String supplySource; // 운영에 필요한 재료나 용품 구매처

    @Lob
    @JsonProperty("aiAnalysis")
    @Column(name = "ai_analysis", columnDefinition = "TEXT")
    private String aiAnalysis; // ai 분석 결과
}