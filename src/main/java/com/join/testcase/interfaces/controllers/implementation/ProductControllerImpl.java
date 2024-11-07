package com.join.testcase.interfaces.controllers.implementation;

import com.join.testcase.application.services.ProductService;
import com.join.testcase.interfaces.controllers.ProductController;
import com.join.testcase.interfaces.dto.ProductCreateRequestDTO;
import com.join.testcase.interfaces.dto.ProductDTO;
import com.join.testcase.interfaces.dto.ProductUpdateRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductControllerImpl implements ProductController<ProductDTO, ProductUpdateRequestDTO, ProductCreateRequestDTO> {

    private static final String DEFAULT_SIZE = "5";
    private static final String DEFAULT_PAGE = "0";
    private static final String SIZE = "size";
    private static final String PAGE = "page";

    private ProductService<ProductDTO, ProductCreateRequestDTO, ProductUpdateRequestDTO> service;

    @Override
    @GetMapping
    public Page<ProductDTO> findAll(
            @RequestParam(name = PAGE, required = false, defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(name = SIZE, required = false, defaultValue = DEFAULT_SIZE) final int size
    ) {
        return this.service.findAll(page, size);
    }

    @Override
    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable final Long id) {
        return this.service.findById(id);
    }

    @Override
    @PostMapping
    public ProductDTO create(@RequestBody final ProductCreateRequestDTO createRequestDTO) {
        return this.service.create(createRequestDTO);
    }

    @Override
    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable final Long id, @RequestBody final ProductUpdateRequestDTO updateRequestDTO) {
        return this.service.update(id, updateRequestDTO);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        this.service.delete(id);
    }
}
