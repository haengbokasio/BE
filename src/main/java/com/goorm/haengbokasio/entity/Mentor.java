package com.goorm.haengbokasio.entity;

import com.goorm.haengbokasio.converter.CustomerAgeGroupConverter;
import com.goorm.haengbokasio.converter.MarketingMethodConverter;
import com.goorm.haengbokasio.enums.CustomerAgeGroup;
import com.goorm.haengbokasio.enums.MarketingMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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
    private String kakaoId;

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

    @Convert(converter = CustomerAgeGroupConverter.class)
    @Column(name = "target_customer", columnDefinition = "TEXT")
    private Set<CustomerAgeGroup> targetCustomer = new HashSet<>(); // 주 고객 - enum

    @Convert(converter = CustomerAgeGroupConverter.class)
    @Column(name = "customer_acquisition_method", columnDefinition = "TEXT")
    private Set<CustomerAgeGroup> customerAcquisitionMethod = new HashSet<>(); // 타겟층 - enum

    @Convert(converter = MarketingMethodConverter.class)
    @Column(name = "marketing_method", columnDefinition = "TEXT")
    private Set<MarketingMethod> marketingMethod = new HashSet<>(); // 홍보 방법 - enum

    @Lob
    @Column(name = "operation_method", columnDefinition = "TEXT")
    private String operationMethod; // 운영 방법

    @Lob
    @Column(name = "supply_source", columnDefinition = "TEXT")
    private String supplySource; // 운영에 필요한 재료나 용품 구매처

    @Column(name = "disabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean disabled = false;
}