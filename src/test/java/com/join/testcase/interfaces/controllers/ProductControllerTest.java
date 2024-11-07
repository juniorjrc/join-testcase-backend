package com.join.testcase.interfaces.controllers;

import com.join.testcase.annotations.IntegrationTest;
import com.join.testcase.infrastructure.exceptions.JoinTestCaseExceptionResponse;
import com.join.testcase.interfaces.dto.ProductDTO;
import com.join.testcase.utils.JoinTestCaseHttpRequestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;

import static com.join.testcase.utils.JoinTestCaseHttpMethodUtils.DELETE;
import static com.join.testcase.utils.JoinTestCaseHttpMethodUtils.GET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@IntegrationTest
@Sql("classpath:sql/truncate.sql")
@Sql("classpath:sql/ProductController.sql")
class ProductControllerTest extends JoinTestCaseHttpRequestUtils {

    private static final String PRODUCT_CONTEXT_PATH = "/product";
    private static final String PRODUCT_10_CONTEXT_PATH = "/product/10";
    private static final String PRODUCT_2000_CONTEXT_PATH = "/product/2000";
    private static final String DELL_NOTEBOOK = "Dell notebook";
    private static final String IPHONE_16 = "Iphone 16";
    private static final String SAMSUNG_TAB = "Samsung Tab";
    private static final String PRODUCT_NOT_FOUND = "Product not found!";

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    void mustGetAllProductsCorrectly() throws Exception {
        String jsonResponse = executeHttpRequest(
                GET,
                PRODUCT_CONTEXT_PATH,
                null,
                new HashMap<>()
        ).getResponse().getContentAsString();
        Page<ProductDTO> productDTOPage = fromJsonToPage(jsonResponse, ProductDTO.class);
        List<ProductDTO> productDTOList = productDTOPage.getContent().stream().toList();
        assertEquals(1, productDTOPage.getTotalPages());
        assertEquals(4, productDTOPage.getTotalElements());
        assertEquals(DELL_NOTEBOOK, productDTOList.getFirst().productName());
        assertEquals(IPHONE_16, productDTOList.get(1).productName());
        assertEquals(SAMSUNG_TAB, productDTOList.get(2).productName());
    }

    @Test
    void mustGetProductByIdCorrectly() throws Exception {
        String jsonResponse = executeHttpRequest(
                GET,
                PRODUCT_10_CONTEXT_PATH,
                null,
                new HashMap<>()
        ).getResponse().getContentAsString();
        ProductDTO productDTO = fromJson(jsonResponse, ProductDTO.class);
        assertEquals(IPHONE_16, productDTO.productName());
    }

    @Test
    void mustThrowNotFoundWhenProductNotFound() throws Exception {
        String jsonResponse = executeHttpRequest(
                GET,
                PRODUCT_2000_CONTEXT_PATH,
                null,
                new HashMap<>()
        ).getResponse().getContentAsString();
        JoinTestCaseExceptionResponse response = fromJson(jsonResponse, JoinTestCaseExceptionResponse.class);
        assertEquals(NOT_FOUND.name(), response.code());
        assertEquals(PRODUCT_NOT_FOUND, response.message());
    }

    @Test
    void mustDeleteProductByIdCorrectly() throws Exception {
        MvcResult result = executeHttpRequest(
                DELETE,
                PRODUCT_10_CONTEXT_PATH,
                null,
                new HashMap<>()
        );
        assertEquals(NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @TestConfiguration
    static class MockMvcTestConfiguration {
        @Bean
        public MockMvc mockMvc(WebApplicationContext context) {
            return webAppContextSetup(context).build();
        }
    }
}