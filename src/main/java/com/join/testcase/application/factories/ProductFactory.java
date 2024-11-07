package com.join.testcase.application.factories;

import com.join.testcase.domain.entities.Category;
import com.join.testcase.domain.entities.Product;
import com.join.testcase.domain.template.implementation.CellPhonesProductTemplate;
import com.join.testcase.domain.template.implementation.ComputersProductTemplate;
import com.join.testcase.domain.template.implementation.ConsolesProductTemplate;
import com.join.testcase.domain.template.implementation.TabletsProductTemplate;
import com.join.testcase.interfaces.dto.ProductCreateRequestDTO;
import com.join.testcase.interfaces.dto.ProductUpdateRequestDTO;
import com.join.testcase.interfaces.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductFactory {

    private final CellPhonesProductTemplate cellPhonesProductTemplate;
    private final TabletsProductTemplate tabletsProductTemplate;
    private final ComputersProductTemplate computersProductTemplate;
    private final ConsolesProductTemplate consolesProductTemplate;

    public Product createProduct(final ProductCreateRequestDTO productCreateRequestDTO, final Category category) {
        CategoryEnum categoryEnum = category.getCategoryName();
        return switch (categoryEnum) {
            case CELL_PHONES -> cellPhonesProductTemplate.createProduct(productCreateRequestDTO, category);
            case TABLETS -> tabletsProductTemplate.createProduct(productCreateRequestDTO, category);
            case COMPUTERS -> computersProductTemplate.createProduct(productCreateRequestDTO, category);
            case CONSOLES -> consolesProductTemplate.createProduct(productCreateRequestDTO, category);
        };
    }

    public Product updateProduct(final Long id, final ProductUpdateRequestDTO productUpdateRequestDTO, final Category category) {
        CategoryEnum categoryEnum = category.getCategoryName();
        return switch (categoryEnum) {
            case CELL_PHONES -> cellPhonesProductTemplate.updateProduct(id, productUpdateRequestDTO, category);
            case TABLETS -> tabletsProductTemplate.updateProduct(id, productUpdateRequestDTO, category);
            case COMPUTERS -> computersProductTemplate.updateProduct(id, productUpdateRequestDTO, category);
            case CONSOLES -> consolesProductTemplate.updateProduct(id, productUpdateRequestDTO, category);
        };
    }
}
