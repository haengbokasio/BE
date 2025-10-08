package com.goorm.haengbokasio.dto;

import com.goorm.haengbokasio.entity.Menti;
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
public class MentiRegisterDto {

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
    private CustomerAgeGroup targetCustomer;
    private CustomerAgeGroup customerAcquisitionMethod;
    private MarketingMethod marketingMethod;
    private String aiAnalysis;


    public Menti toEntity() {
        Menti menti = new Menti();

        menti.setKakaoId(this.kakaoId);
        menti.setPhoneNumber(this.phoneNumber);
        menti.setBusinessType(this.businessType);
        menti.setBusinessDetail(this.businessDetail);
        menti.setOperationPeriod(this.operationPeriod);
        menti.setMonthAvgRevenue(this.monthAvgRevenue);
        menti.setWeekAvgDailyRevenue(this.weekAvgDailyRevenue);
        menti.setBusinessAddress(this.businessAddress);
        menti.setMainProductService(this.mainProductService);
        menti.setOperationMethod(this.operationMethod);
        menti.setSupplySource(this.supplySource);
        menti.setAiAnalysis(this.aiAnalysis);
        menti.setTargetCustomer(this.targetCustomer);
        menti.setCustomerAcquisitionMethod(this.customerAcquisitionMethod);
        menti.setMarketingMethod(this.marketingMethod);

        return menti;
    }
}