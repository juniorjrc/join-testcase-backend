package com.join.testcase.application.services.implementation;

import com.join.testcase.application.factories.ProductFactory;
import com.join.testcase.application.services.ProductService;
import com.join.testcase.domain.entities.Category;
import com.join.testcase.domain.entities.Product;
import com.join.testcase.domain.repositories.ProductRepository;
import com.join.testcase.infrastructure.config.mapper.ProductMapper;
import com.join.testcase.infrastructure.exceptions.NotFoundException;
import com.join.testcase.interfaces.dto.ProductCreateRequestDTO;
import com.join.testcase.interfaces.dto.ProductDTO;
import com.join.testcase.interfaces.dto.ProductUpdateRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService<ProductDTO, ProductCreateRequestDTO, ProductUpdateRequestDTO> {

    private static final String PRODUCT_NOT_FOUND = "Product not found!";

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryServiceImpl categoryService;
    private final ProductFactory productFactory;

    @Override
    public Page<ProductDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("productName")));
        Page<Product> productPage = this.productRepository.findAll(pageable);
        return productPage.map(this.productMapper::toProductDTO);
    }

    @Override
    public ProductDTO findById(Long id) {
        return this.productMapper.toProductDTO(getProductById(id));
    }

    @Override
    public ProductDTO create(ProductCreateRequestDTO createRequestDTO) {
        Category category = this.categoryService.getCategoryById(createRequestDTO.categoryId());
        Product newProduct = this.productFactory.createProduct(createRequestDTO, category);
        return this.productMapper.toProductDTO(this.productRepository.save(newProduct));
    }

    @Override
    public ProductDTO update(Long id, ProductUpdateRequestDTO updateRequestDTO) {
        Product product = getProductById(id);
        Product upToDateProduct = this.productFactory.updateProduct(id, updateRequestDTO, product.getCategory());
        return this.productMapper.toProductDTO(this.productRepository.save(upToDateProduct));
    }

    @Override
    public void delete(Long id) {
        Product product = getProductById(id);
        this.productRepository.delete(product);
    }

    private Product getProductById(final Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND));
    }
}
