package com.join.testcase.domain.template;

import com.join.testcase.domain.entities.Category;
import com.join.testcase.domain.entities.Product;
import com.join.testcase.infrastructure.exceptions.BadRequestException;
import com.join.testcase.interfaces.dto.ProductCreateRequestDTO;
import com.join.testcase.interfaces.dto.ProductUpdateRequestDTO;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

public abstract class ProductTemplate {

    private static final String PRICE_IS_MANDATORY = "You must inform the price of the product!";
    private static final String PRICE_BELLOW_CATEGORY_PRICE = "This price is below what is allowed in the %s category";
    private static final String PRICE_ABOVE_CATEGORY_PRICE = "This price is above what is allowed in the %s category";

    public final Product createProduct(final ProductCreateRequestDTO productCreateRequestDTO, final Category category) {
        validatePrice(productCreateRequestDTO.price());
        validatePriceIsInCategoryRange(productCreateRequestDTO.price(), category);
        validateProductByCategoryInCreate(productCreateRequestDTO);
        return buildProductToCreate(productCreateRequestDTO, category);
    }

    public final Product updateProduct(final Long id, final ProductUpdateRequestDTO productUpdateRequestDTO, final Category category) {
        validatePrice(productUpdateRequestDTO.price());
        validatePriceIsInCategoryRange(productUpdateRequestDTO.price(), category);
        validateProductByCategoryInUpdate(productUpdateRequestDTO);
        return buildProductToUpdate(id, productUpdateRequestDTO, category);
    }

    protected void validatePriceIsInCategoryRange(final BigDecimal price,
                                                  final Category category) {
        if (price.compareTo(category.getMinPrice()) < 0) {
            throw new BadRequestException(String.format(PRICE_BELLOW_CATEGORY_PRICE, category.getCategoryName()));
        }

        if(price.compareTo(category.getMaxPrice()) > 0) {
            throw new BadRequestException(String.format(PRICE_ABOVE_CATEGORY_PRICE, category.getCategoryName()));
        }
    }

    protected void validatePrice(final BigDecimal price) {
        if(isNull(price)) {
            throw new BadRequestException(PRICE_IS_MANDATORY);
        }
    }

    protected abstract void validateProductByCategoryInCreate(final ProductCreateRequestDTO productCreateRequestDTO);
    protected abstract void validateProductByCategoryInUpdate(final ProductUpdateRequestDTO productUpdateRequestDTO);

    protected Product buildProductToCreate(final ProductCreateRequestDTO productCreateRequestDTO, final Category category) {
        return Product.builder()
                .productName(productCreateRequestDTO.productName())
                .productDescription(productCreateRequestDTO.productDescription())
                .category(category)
                .price(productCreateRequestDTO.price())
                .technicalSpecifications(productCreateRequestDTO.technicalSpecifications())
                .imeiNumber(productCreateRequestDTO.imeiNumber())
                .operatingSystem(productCreateRequestDTO.operatingSystem())
                .build();
    }

    protected Product buildProductToUpdate(final Long id, final ProductUpdateRequestDTO productUpdateRequestDTO, final Category category) {
        return Product.builder()
                .id(id)
                .productName(productUpdateRequestDTO.productName())
                .productDescription(productUpdateRequestDTO.productDescription())
                .category(category)
                .price(productUpdateRequestDTO.price())
                .technicalSpecifications(productUpdateRequestDTO.technicalSpecifications())
                .imeiNumber(productUpdateRequestDTO.imeiNumber())
                .operatingSystem(productUpdateRequestDTO.operatingSystem())
                .build();
    }
}
