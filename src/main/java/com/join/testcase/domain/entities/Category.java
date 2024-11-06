package com.join.testcase.domain.entities;

import com.join.testcase.infrastructure.config.converter.CategoryEnumConverter;
import com.join.testcase.interfaces.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "JOIN_CATEGORY")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Convert(converter = CategoryEnumConverter.class)
    @Column(name = "CATEGORY_NAME", nullable = false, length = 150)
    private CategoryEnum categoryName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "MIN_PRICE")
    private BigDecimal minPrice;

    @Column(name = "MAX_PRICE")
    private BigDecimal maxPrice;
}
