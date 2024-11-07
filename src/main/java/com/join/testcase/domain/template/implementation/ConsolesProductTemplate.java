package com.join.testcase.domain.template.implementation;

import com.join.testcase.domain.template.ProductTemplate;
import com.join.testcase.interfaces.dto.ProductCreateRequestDTO;
import com.join.testcase.interfaces.dto.ProductUpdateRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class ConsolesProductTemplate extends ProductTemplate {

    @Override
    protected void validateProductByCategoryInCreate(ProductCreateRequestDTO productCreateRequestDTO) {
        //Do nothing
    }

    @Override
    protected void validateProductByCategoryInUpdate(ProductUpdateRequestDTO productUpdateRequestDTO) {
        //Do nothing
    }
}
