package com.join.testcase.interfaces.dto;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String productName,
        String productDescription,
        CategoryDTO category,
        BigDecimal price,
        String technicalSpecifications,
        String imeiNumber,
        String operatingSystem
) {
}
