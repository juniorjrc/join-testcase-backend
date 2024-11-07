package com.join.testcase.domain.template.implementation;

import com.join.testcase.domain.template.ProductTemplate;
import com.join.testcase.infrastructure.exceptions.BadRequestException;
import com.join.testcase.interfaces.dto.ProductCreateRequestDTO;
import com.join.testcase.interfaces.dto.ProductUpdateRequestDTO;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class ComputersProductTemplate extends ProductTemplate {

    private static final String OPERATING_SYSTEM_ARE_REQUIRED = "For this product, operating system are required!";

    @Override
    protected void validateProductByCategoryInCreate(ProductCreateRequestDTO productCreateRequestDTO) {
        hasOperatingSystem(productCreateRequestDTO.operatingSystem());
    }

    @Override
    protected void validateProductByCategoryInUpdate(ProductUpdateRequestDTO productUpdateRequestDTO) {
        hasOperatingSystem(productUpdateRequestDTO.operatingSystem());
    }

    private void hasOperatingSystem(final String operatingSystem) {
        if(isNull(operatingSystem) || EMPTY.equals(operatingSystem)) {
            throw new BadRequestException(OPERATING_SYSTEM_ARE_REQUIRED);
        }
    }
}
