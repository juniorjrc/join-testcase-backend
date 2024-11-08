package com.join.testcase.interfaces.controllers;

import com.join.testcase.annotations.IntegrationTest;
import com.join.testcase.interfaces.dto.CategoryDTO;
import com.join.testcase.interfaces.dto.CategoryUpdateRequestDTO;
import com.join.testcase.utils.JoinTestCaseHttpRequestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static com.join.testcase.interfaces.enums.CategoryEnum.*;
import static com.join.testcase.utils.JoinTestCaseHttpMethodUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@IntegrationTest
@Sql("classpath:sql/truncate.sql")
@Sql("classpath:sql/CategoryController.sql")
class CategoryControllerTest extends JoinTestCaseHttpRequestUtils {

    private static final String CATEGORY_3_CONTEXT_PATH = "/category/3";
    private static final String CATEGORY_4_CONTEXT_PATH = "/category/4";
    private static final String CATEGORY_5_CONTEXT_PATH = "/category/5";
    private static final String CATEGORY_CONTEXT_PATH = "/category";
    private static final String CATEGORY_DESCRIPTION = "Computers description";

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    void mustGetAllCategoriesCorrectly() throws Exception {
        String jsonResponse = executeHttpRequest(
                GET,
                CATEGORY_CONTEXT_PATH,
                null,
                new HashMap<>()
        ).getResponse().getContentAsString();
        Page<CategoryDTO> categoryDTOPage = fromJsonToPage(jsonResponse, CategoryDTO.class);
        List<CategoryDTO> categoryDTOList = categoryDTOPage.getContent().stream().toList();
        assertEquals(1, categoryDTOPage.getTotalPages());
        assertEquals(4, categoryDTOPage.getTotalElements());
        assertEquals(CELL_PHONES.getCategoryName(), categoryDTOList.getFirst().categoryName());
        assertEquals(COMPUTERS.getCategoryName(), categoryDTOList.get(1).categoryName());
        assertEquals(CONSOLES.getCategoryName(), categoryDTOList.get(2).categoryName());
        assertEquals(TABLETS.getCategoryName(), categoryDTOList.get(3).categoryName());
    }

    @Test
    void mustGetCategoryByIdCorrectly() throws Exception {
        String jsonResponse = executeHttpRequest(
                GET,
                CATEGORY_3_CONTEXT_PATH,
                null,
                new HashMap<>()
        ).getResponse().getContentAsString();
        CategoryDTO categoryDTO = fromJson(jsonResponse, CategoryDTO.class);
        assertEquals(COMPUTERS.getCategoryName(), categoryDTO.categoryName());
    }

    @Test
    @Rollback
    void mustUpdateByIdCorrectly() throws Exception {
        CategoryUpdateRequestDTO updateRequestDTO = new CategoryUpdateRequestDTO(
                CATEGORY_DESCRIPTION,
                BigDecimal.ONE,
                BigDecimal.TWO
        );
        String jsonResponse = executeHttpRequest(
                PUT,
                CATEGORY_3_CONTEXT_PATH,
                updateRequestDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        CategoryDTO categoryDTO = fromJson(jsonResponse, CategoryDTO.class);
        assertEquals(COMPUTERS.getCategoryName(), categoryDTO.categoryName());
        assertEquals(CATEGORY_DESCRIPTION, categoryDTO.description());
        assertEquals(BigDecimal.ONE, categoryDTO.minPrice());
        assertEquals(BigDecimal.TWO, categoryDTO.maxPrice());
    }

    @Test
    void mustThrowNotFoundWhenCategoryNotFoundByIdCorrectly() throws Exception {
        CategoryUpdateRequestDTO updateRequestDTO = new CategoryUpdateRequestDTO(
                CATEGORY_DESCRIPTION,
                BigDecimal.ONE,
                BigDecimal.TWO
        );
        MvcResult result = executeHttpRequest(
                PUT,
                CATEGORY_5_CONTEXT_PATH,
                updateRequestDTO,
                new HashMap<>()
        );
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    @Rollback
    void mustDeleteCategoryCorrectly() throws Exception {
        MvcResult result = executeHttpRequest(
                DELETE,
                CATEGORY_4_CONTEXT_PATH,
                null,
                new HashMap<>()
        );
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    @Rollback
    void mustThrowBadRequestInDataIntegrityExceptionInDeleteCategory() throws Exception {
        MvcResult result = executeHttpRequest(
                DELETE,
                CATEGORY_3_CONTEXT_PATH,
                null,
                new HashMap<>()
        );
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @TestConfiguration
    static class MockMvcTestConfiguration {
        @Bean
        public MockMvc mockMvc(WebApplicationContext context) {
            return webAppContextSetup(context).build();
        }
    }
}