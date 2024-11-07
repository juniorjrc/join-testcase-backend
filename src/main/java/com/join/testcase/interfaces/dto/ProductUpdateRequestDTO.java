package com.join.testcase.interfaces.dto;

import java.math.BigDecimal;

public record ProductUpdateRequestDTO(
        String productName,
        String productDescription,
        BigDecimal price,
        String technicalSpecifications,
        String imeiNumber,
        String operatingSystem
) {
}
