package com.goorm.haengbokasio.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.haengbokasio.enums.CustomerAgeGroup;
import com.goorm.haengbokasio.enums.MarketingMethod;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String customerAgeGroupsToString(Set<CustomerAgeGroup> ageGroups) {
        if (ageGroups == null || ageGroups.isEmpty()) {
            return null;
        }

        List<String> codes = ageGroups.stream()
                .map(CustomerAgeGroup::getCode)
                .collect(Collectors.toList());

        try {
            return objectMapper.writeValueAsString(codes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize CustomerAgeGroups", e);
        }
    }

    public static Set<CustomerAgeGroup> stringToCustomerAgeGroups(String json) {
        if (json == null || json.trim().isEmpty()) {
            return Set.of();
        }

        try {
            List<String> codes = objectMapper.readValue(json, new TypeReference<List<String>>() {});
            return codes.stream()
                    .map(CustomerAgeGroup::fromCode)
                    .collect(Collectors.toSet());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize CustomerAgeGroups", e);
        }
    }

    public static String marketingMethodsToString(Set<MarketingMethod> methods) {
        if (methods == null || methods.isEmpty()) {
            return null;
        }

        List<String> codes = methods.stream()
                .map(MarketingMethod::getCode)
                .collect(Collectors.toList());

        try {
            return objectMapper.writeValueAsString(codes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize MarketingMethods", e);
        }
    }

    public static Set<MarketingMethod> stringToMarketingMethods(String json) {
        if (json == null || json.trim().isEmpty()) {
            return Set.of();
        }

        try {
            List<String> codes = objectMapper.readValue(json, new TypeReference<List<String>>() {});
            return codes.stream()
                    .map(MarketingMethod::fromCode)
                    .collect(Collectors.toSet());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize MarketingMethods", e);
        }
    }
}
