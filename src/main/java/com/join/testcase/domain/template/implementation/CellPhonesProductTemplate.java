package com.join.testcase.domain.template.implementation;

import com.join.testcase.domain.template.ProductTemplate;
import com.join.testcase.infrastructure.exceptions.BadRequestException;
import com.join.testcase.interfaces.dto.ProductCreateRequestDTO;
import com.join.testcase.interfaces.dto.ProductUpdateRequestDTO;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class CellPhonesProductTemplate extends ProductTemplate {

    private static final String IMEI_NUMBER_MANDATORY = "For this product, the imei number is mandatory!";

    @Override
    protected void validateProductByCategoryInCreate(ProductCreateRequestDTO productCreateRequestDTO) {
        hasImeiNumber(productCreateRequestDTO.imeiNumber());
    }

    @Override
    protected void validateProductByCategoryInUpdate(ProductUpdateRequestDTO productUpdateRequestDTO) {
        hasImeiNumber(productUpdateRequestDTO.imeiNumber());
    }

    private void hasImeiNumber(final String imeiNumber) {
        if(isNull(imeiNumber) || EMPTY.equals(imeiNumber)) {
            throw new BadRequestException(IMEI_NUMBER_MANDATORY);
        }
    }
}
