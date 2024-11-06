package com.join.testcase.application.services;

import com.join.testcase.domain.entities.Category;
import com.join.testcase.domain.repositories.CategoryRepository;
import com.join.testcase.infrastructure.config.mapper.CategoryMapper;
import com.join.testcase.infrastructure.exceptions.BadRequestException;
import com.join.testcase.infrastructure.exceptions.NotFoundException;
import com.join.testcase.interfaces.dto.CategoryDTO;
import com.join.testcase.interfaces.dto.CategoryUpdateRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService implements JoinTestCaseService<CategoryDTO, CategoryUpdateRequestDTO> {

    private static final String CATEGORY_NOT_FOUND = "Category not found with this id!";
    private static final String ITS_NOT_POSSIBLE_REMOVE_CATEGORY = "It is not possible to remove this record because it is linked to other operations within the system!";

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDTO> findAll(final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("categoryName")));
        Page<Category> categoryPage = this.categoryRepository.findAll(pageable);
        return categoryPage.map(this.categoryMapper::toCategoryDTO);
    }

    @Override
    public CategoryDTO findById(final Long id) {
        return this.categoryMapper.toCategoryDTO(getCategoryById(id));
    }

    @Override
    public CategoryDTO update(final Long id, final CategoryUpdateRequestDTO updateRequestDTO) {
        Category category = getCategoryById(id);
        category.setDescription(updateRequestDTO.description());
        category.setMinPrice(updateRequestDTO.minPrice());
        category.setMaxPrice(updateRequestDTO.maxPrice());
        return this.categoryMapper.toCategoryDTO(this.categoryRepository.save(category));
    }

    @Override
    public void delete(final Long id) {
        Category category = getCategoryById(id);
        try {
            this.categoryRepository.delete(category);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(ITS_NOT_POSSIBLE_REMOVE_CATEGORY);
        }
    }

    private Category getCategoryById(final Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND));
    }
}
