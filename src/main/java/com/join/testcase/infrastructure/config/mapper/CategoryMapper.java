package com.join.testcase.infrastructure.config.mapper;

import com.join.testcase.domain.entities.Category;
import com.join.testcase.interfaces.dto.CategoryDTO;
import com.join.testcase.interfaces.enums.CategoryEnum;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toCategoryDTO(Category category);

    default String map(CategoryEnum categoryEnum) {
        return categoryEnum != null ? categoryEnum.getCategoryName() : null;
    }
}
