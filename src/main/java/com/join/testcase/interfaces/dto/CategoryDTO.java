package com.join.testcase.interfaces.dto;

import java.math.BigDecimal;

public record CategoryDTO(
        Long id,
        String categoryName,
        String description,
        BigDecimal minPrice,
        BigDecimal maxPrice
) {}
