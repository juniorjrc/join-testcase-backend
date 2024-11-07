package com.join.testcase.domain.template.implementation;

import com.join.testcase.domain.template.ProductTemplate;
import com.join.testcase.infrastructure.exceptions.BadRequestException;
import com.join.testcase.interfaces.dto.ProductCreateRequestDTO;
import com.join.testcase.interfaces.dto.ProductUpdateRequestDTO;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class TabletsProductTemplate extends ProductTemplate {

    private static final String TECHNICAL_SPECIFICATIONS_ARE_REQUIRED = "For this product, the technical specifications are required!";

    @Override
    protected void validateProductByCategoryInCreate(ProductCreateRequestDTO productCreateRequestDTO) {
        hasTechnicalSpecifications(productCreateRequestDTO.technicalSpecifications());
    }

    @Override
    protected void validateProductByCategoryInUpdate(ProductUpdateRequestDTO productUpdateRequestDTO) {
        hasTechnicalSpecifications(productUpdateRequestDTO.technicalSpecifications());
    }

    private void hasTechnicalSpecifications(final String technicalSpecifications) {
        if (isNull(technicalSpecifications) || EMPTY.equals(technicalSpecifications)) {
            throw new BadRequestException(TECHNICAL_SPECIFICATIONS_ARE_REQUIRED);
        }
    }
}
