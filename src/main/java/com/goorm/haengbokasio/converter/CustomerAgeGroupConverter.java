package com.goorm.haengbokasio.converter;

import com.goorm.haengbokasio.enums.CustomerAgeGroup;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Set;

@Converter
public class CustomerAgeGroupConverter implements AttributeConverter<Set<CustomerAgeGroup>, String> {

    @Override
    public String convertToDatabaseColumn(Set<CustomerAgeGroup> attribute) {
        return EnumConverter.customerAgeGroupsToString(attribute);
    }

    @Override
    public Set<CustomerAgeGroup> convertToEntityAttribute(String dbData) {
        return EnumConverter.stringToCustomerAgeGroups(dbData);
    }
}
