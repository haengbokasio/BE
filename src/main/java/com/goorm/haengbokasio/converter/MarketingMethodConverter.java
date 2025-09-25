package com.goorm.haengbokasio.converter;

import com.goorm.haengbokasio.enums.MarketingMethod;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Set;

@Converter
public class MarketingMethodConverter implements AttributeConverter<Set<MarketingMethod>, String> {

    @Override
    public String convertToDatabaseColumn(Set<MarketingMethod> attribute) {
        return EnumConverter.marketingMethodsToString(attribute);
    }

    @Override
    public Set<MarketingMethod> convertToEntityAttribute(String dbData) {
        return EnumConverter.stringToMarketingMethods(dbData);
    }
}
