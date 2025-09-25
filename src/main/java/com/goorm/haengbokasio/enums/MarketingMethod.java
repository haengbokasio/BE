package com.goorm.haengbokasio.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MarketingMethod {
    SNS_OPERATION("SNS 운영", "SNS 운영"),
    SEARCH_AD("검색 광고", "검색 광고"),
    SNS_AD("SNS 광고", "SNS 광고"),
    EXPERIENCE("체험단", "체험단"),
    OTHER("기타", "기타");

    private final String code;
    private final String description;

    public static MarketingMethod fromCode(String code) {
        for (MarketingMethod method : values()) {
            if (method.getCode().equals(code)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Invalid MarketingMethod code: " + code);
    }

    public static MarketingMethod fromDescription(String description) {
        for (MarketingMethod method : values()) {
            if (method.getDescription().equals(description)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Invalid MarketingMethod description: " + description);
    }
}
