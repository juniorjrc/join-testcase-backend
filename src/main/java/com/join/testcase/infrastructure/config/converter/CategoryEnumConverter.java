package com.join.testcase.infrastructure.config.converter;

import com.join.testcase.interfaces.enums.CategoryEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryEnumConverter implements AttributeConverter<CategoryEnum, String> {

    @Override
    public String convertToDatabaseColumn(CategoryEnum attribute) {
        return attribute != null ? attribute.getCategoryName() : null;
    }

    @Override
    public CategoryEnum convertToEntityAttribute(String dbData) {
        for (CategoryEnum category : CategoryEnum.values()) {
            if (category.getCategoryName().equals(dbData)) {
                return category;
            }
        }
        return null;
    }
}
