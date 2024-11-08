package com.join.testcase.interfaces.controllers.implementation;

import com.join.testcase.application.services.CategoryService;
import com.join.testcase.interfaces.controllers.CategoryController;
import com.join.testcase.interfaces.dto.CategoryDTO;
import com.join.testcase.interfaces.dto.CategoryUpdateRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryControllerImpl implements CategoryController<CategoryDTO, CategoryUpdateRequestDTO> {

    private static final String DEFAULT_SIZE = "5";
    private static final String DEFAULT_PAGE = "0";
    private static final String SIZE = "size";
    private static final String PAGE = "page";

    private final CategoryService<CategoryDTO, CategoryUpdateRequestDTO> service;

    @Override
    @GetMapping
    public Page<CategoryDTO> findAll(
            @RequestParam(name = PAGE, required = false, defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(name = SIZE, required = false, defaultValue = DEFAULT_SIZE) final int size
    ) {
        return this.service.findAll(page, size);
    }

    @Override
    @GetMapping("/{id}")
    public CategoryDTO findById(@PathVariable final Long id) {
        return this.service.findById(id);
    }

    @Override
    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable final Long id, @RequestBody final CategoryUpdateRequestDTO updateRequestDTO) {
        return this.service.update(id, updateRequestDTO);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        this.service.delete(id);
    }
}
