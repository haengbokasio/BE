package com.goorm.haengbokasio.dto;

import com.goorm.haengbokasio.entity.Mentor;
import com.goorm.haengbokasio.enums.CustomerAgeGroup;
import com.goorm.haengbokasio.enums.MarketingMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MentorRegisterDto {

    private Long kakaoId;
    private String phoneNumber;
    private String businessType;
    private String businessDetail;
    private String businessAddress;
    private String mainProductService;
    private String operationMethod;
    private String supplySource;
    private Integer operationPeriod;
    private Integer monthAvgRevenue;
    private Integer weekAvgDailyRevenue;
    private String targetCustomer;
    private String customerAcquisitionMethod;
    private String marketingMethod;
    private String aiAnalysis;


    public Mentor toEntity() {
        Mentor mentor = new Mentor();

        mentor.setKakaoId(this.kakaoId);
        mentor.setPhoneNumber(this.phoneNumber);
        mentor.setBusinessType(this.businessType);
        mentor.setBusinessDetail(this.businessDetail);
        mentor.setOperationPeriod(this.operationPeriod);
        mentor.setMonthAvgRevenue(this.monthAvgRevenue);
        mentor.setWeekAvgDailyRevenue(this.weekAvgDailyRevenue);
        mentor.setBusinessAddress(this.businessAddress);
        mentor.setMainProductService(this.mainProductService);
        mentor.setOperationMethod(this.operationMethod);
        mentor.setSupplySource(this.supplySource);
        mentor.setAiAnalysis(this.aiAnalysis);
        mentor.setTargetCustomer(this.targetCustomer);
        mentor.setCustomerAcquisitionMethod(this.customerAcquisitionMethod);
        mentor.setMarketingMethod(this.marketingMethod);

        return mentor;
    }
}