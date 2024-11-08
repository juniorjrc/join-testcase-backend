package com.join.testcase.interfaces.dto;

import java.math.BigDecimal;

public record ProductCreateRequestDTO(
        String productName,
        String productDescription,
        Long categoryId,
        BigDecimal price,
        String technicalSpecifications,
        String imeiNumber,
        String operatingSystem
) {
}
