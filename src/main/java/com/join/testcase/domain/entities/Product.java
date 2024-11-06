package com.join.testcase.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "JOIN_PRODUCT")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "PRODUCT_NAME", length = 150, nullable = false)
    private String productName;

    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID", nullable=false)
    private Category category;

    @Column(name = "PRICE")
    private BigDecimal price;
}
