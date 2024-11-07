package com.join.testcase.infrastructure.config.mapper;

import com.join.testcase.domain.entities.Category;
import com.join.testcase.domain.entities.Product;
import com.join.testcase.interfaces.dto.CategoryDTO;
import com.join.testcase.interfaces.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toProductDTO(final Product product);

    default CategoryDTO mapCategoryToCategoryDTO(final Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getCategoryName().getCategoryName(),
                category.getDescription(),
                category.getMinPrice(),
                category.getMaxPrice()
        );
    }
}
