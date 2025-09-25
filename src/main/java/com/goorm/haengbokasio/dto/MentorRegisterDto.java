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
    private Set<CustomerAgeGroup> targetCustomer = new HashSet<>();
    private Set<CustomerAgeGroup> customerAcquisitionMethod = new HashSet<>();
    private Set<MarketingMethod> marketingMethod = new HashSet<>();

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

        if (this.targetCustomer != null) {
            mentor.setTargetCustomer(this.targetCustomer);
        }
        if (this.customerAcquisitionMethod != null) {
            mentor.setCustomerAcquisitionMethod(this.customerAcquisitionMethod);
        }
        if (this.marketingMethod != null) {
            mentor.setMarketingMethod(this.marketingMethod);
        }

        return mentor;
    }
}