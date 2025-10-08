package com.goorm.haengbokasio.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MarketingMethod {
    SNS_OPERATION("SNS 운영"),
    SEARCH_AD("검색 광고"),
    SNS_AD("SNS 광고"),
    EXPERIENCE("체험단"),
    OTHER("기타");

    private final String code;

    public static MarketingMethod fromCode(String code) {
        for (MarketingMethod method : values()) {
            if (method.getCode().equals(code)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Invalid MarketingMethod code: " + code);
    }
}
