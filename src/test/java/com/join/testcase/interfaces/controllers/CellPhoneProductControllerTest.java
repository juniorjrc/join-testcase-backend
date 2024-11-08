package com.join.testcase.interfaces.controllers;

import com.join.testcase.annotations.IntegrationTest;
import com.join.testcase.infrastructure.exceptions.JoinTestCaseExceptionResponse;
import com.join.testcase.interfaces.dto.ProductCreateRequestDTO;
import com.join.testcase.interfaces.dto.ProductDTO;
import com.join.testcase.utils.JoinTestCaseHttpRequestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.HashMap;

import static com.join.testcase.utils.JoinTestCaseHttpMethodUtils.POST;
import static com.join.testcase.utils.JoinTestCaseHttpMethodUtils.PUT;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@IntegrationTest
@Sql("classpath:sql/truncate.sql")
@Sql("classpath:sql/ProductController.sql")
class CellPhoneProductControllerTest extends JoinTestCaseHttpRequestUtils {

    private static final String PRODUCT_CONTEXT_PATH = "/product";
    private static final String PRODUCT_10_CONTEXT_PATH = "/product/10";
    private static final String MOTO_G_25 = "Moto G25";
    private static final long CELL_PHONES_ID = 1L;
    private static final String IMEI_NUMBER = "HHH587987";
    private static final String PRICE_IS_MANDATORY = "You must inform the price of the product!";
    private static final String CELL_PHONE_PRICE_BELLOW_CATEGORY_PRICE = "This price is below what is allowed in the CELL_PHONES category";
    private static final String CELL_PHONE_PRICE_ABOVE_CATEGORY_PRICE = "This price is above what is allowed in the CELL_PHONES category";
    private static final String IMEI_NUMBER_MANDATORY = "For this product, the imei number is mandatory!";
    private static final String IPHONE_16_V_2 = "Iphone 16 v2";


    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    @Rollback
    void mustCreateCellPhoneProductCorrectly() throws Exception {
        final ProductCreateRequestDTO createRequestDTO = new ProductCreateRequestDTO(
                MOTO_G_25,
                EMPTY,
                CELL_PHONES_ID,
                BigDecimal.valueOf(1500),
                null,
                IMEI_NUMBER,
                null
        );
        String jsonResponse = executeHttpRequest(
                POST,
                PRODUCT_CONTEXT_PATH,
                createRequestDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        ProductDTO productDTO = fromJson(jsonResponse, ProductDTO.class);
        assertEquals(MOTO_G_25, productDTO.productName());
        assertEquals(CELL_PHONES_ID, productDTO.category().id());
        assertEquals(BigDecimal.valueOf(1500), productDTO.price());
        assertEquals(IMEI_NUMBER, productDTO.imeiNumber());
    }

    @Test
    void mustNotCreateCellWithoutPrice() throws Exception {
        final ProductCreateRequestDTO createRequestDTO = new ProductCreateRequestDTO(
                MOTO_G_25,
                EMPTY,
                CELL_PHONES_ID,
                null,
                null,
                IMEI_NUMBER,
                null
        );
        String jsonResponse = executeHttpRequest(
                POST,
                PRODUCT_CONTEXT_PATH,
                createRequestDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        JoinTestCaseExceptionResponse response = fromJson(jsonResponse, JoinTestCaseExceptionResponse.class);
        assertEquals(BAD_REQUEST.name(), response.code());
        assertEquals(PRICE_IS_MANDATORY, response.message());
    }

    @Test
    void mustNotCreateCellWhenPriceIsBellowCategoryPrice() throws Exception {
        final ProductCreateRequestDTO createRequestDTO = new ProductCreateRequestDTO(
                MOTO_G_25,
                EMPTY,
                CELL_PHONES_ID,
                BigDecimal.valueOf(700),
                null,
                IMEI_NUMBER,
                null
        );
        String jsonResponse = executeHttpRequest(
                POST,
                PRODUCT_CONTEXT_PATH,
                createRequestDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        JoinTestCaseExceptionResponse response = fromJson(jsonResponse, JoinTestCaseExceptionResponse.class);
        assertEquals(BAD_REQUEST.name(), response.code());
        assertEquals(CELL_PHONE_PRICE_BELLOW_CATEGORY_PRICE, response.message());
    }

    @Test
    void mustNotCreateCellWhenPriceIsAboveCategoryPrice() throws Exception {
        final ProductCreateRequestDTO createRequestDTO = new ProductCreateRequestDTO(
                MOTO_G_25,
                EMPTY,
                CELL_PHONES_ID,
                BigDecimal.valueOf(999999),
                null,
                IMEI_NUMBER,
                null
        );
        String jsonResponse = executeHttpRequest(
                POST,
                PRODUCT_CONTEXT_PATH,
                createRequestDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        JoinTestCaseExceptionResponse response = fromJson(jsonResponse, JoinTestCaseExceptionResponse.class);
        assertEquals(BAD_REQUEST.name(), response.code());
        assertEquals(CELL_PHONE_PRICE_ABOVE_CATEGORY_PRICE, response.message());
    }

    @Test
    void mustNotCreateCellWhenImeiNumberIsNotInformed() throws Exception {
        final ProductCreateRequestDTO createRequestDTO = new ProductCreateRequestDTO(
                MOTO_G_25,
                EMPTY,
                CELL_PHONES_ID,
                BigDecimal.valueOf(19000),
                null,
                null,
                null
        );
        String jsonResponse = executeHttpRequest(
                POST,
                PRODUCT_CONTEXT_PATH,
                createRequestDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        JoinTestCaseExceptionResponse response = fromJson(jsonResponse, JoinTestCaseExceptionResponse.class);
        assertEquals(BAD_REQUEST.name(), response.code());
        assertEquals(IMEI_NUMBER_MANDATORY, response.message());
    }

    @Test
    @Rollback
    void mustUpdateCellPhoneProductCorrectly() throws Exception {
        final ProductCreateRequestDTO createRequestDTO = new ProductCreateRequestDTO(
                IPHONE_16_V_2,
                EMPTY,
                CELL_PHONES_ID,
                BigDecimal.valueOf(1500),
                null,
                IMEI_NUMBER,
                null
        );
        String jsonResponse = executeHttpRequest(
                PUT,
                PRODUCT_10_CONTEXT_PATH,
                createRequestDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        ProductDTO productDTO = fromJson(jsonResponse, ProductDTO.class);
        assertEquals(IPHONE_16_V_2, productDTO.productName());
        assertEquals(EMPTY, productDTO.productDescription());
        assertEquals(CELL_PHONES_ID, productDTO.category().id());
        assertEquals(BigDecimal.valueOf(1500), productDTO.price());
        assertEquals(IMEI_NUMBER, productDTO.imeiNumber());
    }

    @Test
    void mustNotUpdateCellPhoneWhenPriceNotInformed() throws Exception {
        final ProductCreateRequestDTO createRequestDTO = new ProductCreateRequestDTO(
                IPHONE_16_V_2,
                EMPTY,
                CELL_PHONES_ID,
                null,
                null,
                IMEI_NUMBER,
                null
        );
        String jsonResponse = executeHttpRequest(
                PUT,
                PRODUCT_10_CONTEXT_PATH,
                createRequestDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        JoinTestCaseExceptionResponse response = fromJson(jsonResponse, JoinTestCaseExceptionResponse.class);
        assertEquals(BAD_REQUEST.name(), response.code());
        assertEquals(PRICE_IS_MANDATORY, response.message());
    }

    @Test
    void mustNotUpdateCellPhoneWhenPriceBellowCategory() throws Exception {
        final ProductCreateRequestDTO createRequestDTO = new ProductCreateRequestDTO(
                IPHONE_16_V_2,
                EMPTY,
                CELL_PHONES_ID,
                BigDecimal.valueOf(1),
                null,
                IMEI_NUMBER,
                null
        );
        String jsonResponse = executeHttpRequest(
                PUT,
                PRODUCT_10_CONTEXT_PATH,
                createRequestDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        JoinTestCaseExceptionResponse response = fromJson(jsonResponse, JoinTestCaseExceptionResponse.class);
        assertEquals(BAD_REQUEST.name(), response.code());
        assertEquals(CELL_PHONE_PRICE_BELLOW_CATEGORY_PRICE, response.message());
    }

    @Test
    void mustNotUpdateCellPhoneWhenPriceAboveCategory() throws Exception {
        final ProductCreateRequestDTO createRequestDTO = new ProductCreateRequestDTO(
                IPHONE_16_V_2,
                EMPTY,
                CELL_PHONES_ID,
                BigDecimal.valueOf(999999),
                null,
                IMEI_NUMBER,
                null
        );
        String jsonResponse = executeHttpRequest(
                PUT,
                PRODUCT_10_CONTEXT_PATH,
                createRequestDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        JoinTestCaseExceptionResponse response = fromJson(jsonResponse, JoinTestCaseExceptionResponse.class);
        assertEquals(BAD_REQUEST.name(), response.code());
        assertEquals(CELL_PHONE_PRICE_ABOVE_CATEGORY_PRICE, response.message());
    }

    @Test
    void mustNotUpdateCellPhoneWhenImeiNumberNotInformed() throws Exception {
        final ProductCreateRequestDTO createRequestDTO = new ProductCreateRequestDTO(
                IPHONE_16_V_2,
                EMPTY,
                CELL_PHONES_ID,
                BigDecimal.valueOf(18000),
                null,
                null,
                null
        );
        String jsonResponse = executeHttpRequest(
                PUT,
                PRODUCT_10_CONTEXT_PATH,
                createRequestDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        JoinTestCaseExceptionResponse response = fromJson(jsonResponse, JoinTestCaseExceptionResponse.class);
        assertEquals(BAD_REQUEST.name(), response.code());
        assertEquals(IMEI_NUMBER_MANDATORY, response.message());
    }

    @TestConfiguration
    static class MockMvcTestConfiguration {
        @Bean
        public MockMvc mockMvc(WebApplicationContext context) {
            return webAppContextSetup(context).build();
        }
    }
}
