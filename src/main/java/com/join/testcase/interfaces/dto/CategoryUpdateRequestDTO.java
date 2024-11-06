package com.join.testcase.interfaces.dto;

import java.math.BigDecimal;

public record CategoryUpdateRequestDTO(
        String description,
        BigDecimal minPrice,
        BigDecimal maxPrice
) {
}
