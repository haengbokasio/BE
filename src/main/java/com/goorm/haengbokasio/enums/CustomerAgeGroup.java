package com.goorm.haengbokasio.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 고객 연령대 Enum
 */
@Getter
@RequiredArgsConstructor
public enum CustomerAgeGroup {
    TOURIST("관광객", "관광객"),
    TEENS("10대 도민", "10대 도민"),
    TWENTIES_THIRTIES("20~30대 도민", "20~30대 도민"),
    FORTIES_FIFTIES("40~50대 도민", "40~50대 도민"),
    SIXTIES_PLUS("60대 이상", "60대 이상");

    private final String code;
    private final String description;

    public static CustomerAgeGroup fromCode(String code) {
        for (CustomerAgeGroup ageGroup : values()) {
            if (ageGroup.getCode().equals(code)) {
                return ageGroup;
            }
        }
        throw new IllegalArgumentException("Invalid CustomerAgeGroup code: " + code);
    }

    public static CustomerAgeGroup fromDescription(String description) {
        for (CustomerAgeGroup ageGroup : values()) {
            if (ageGroup.getDescription().equals(description)) {
                return ageGroup;
            }
        }
        throw new IllegalArgumentException("Invalid CustomerAgeGroup description: " + description);
    }

    public static boolean isValidCode(String code) {
        try {
            fromCode(code);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}