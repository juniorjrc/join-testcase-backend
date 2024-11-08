package com.join.testcase.interfaces.controllers;

import com.join.testcase.annotations.IntegrationTest;
import com.join.testcase.interfaces.dto.CredentialsDTO;
import com.join.testcase.interfaces.dto.LoggedUserDTO;
import com.join.testcase.utils.JoinTestCaseHttpRequestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static com.join.testcase.utils.JoinTestCaseHttpMethodUtils.POST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@IntegrationTest
@Sql("classpath:sql/truncate.sql")
@Sql("classpath:sql/LoginController.sql")
class LoginControllerTest extends JoinTestCaseHttpRequestUtils {

    private static final String LOGIN_AND_PASSWORD_USER = "joinusr";
    private static final String LOGIN_CONTEXT_PATH = "/login";
    private static final String JOIN_MASTER_USERNAME = "Join Master User";
    private static final String FAKE_PASSWORD = "test";

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    void mustLoginCorrectly() throws Exception{
        final CredentialsDTO credentialsDTO = new CredentialsDTO(LOGIN_AND_PASSWORD_USER, LOGIN_AND_PASSWORD_USER);
        String jsonResponse = executeHttpRequest(
                POST,
                LOGIN_CONTEXT_PATH,
                credentialsDTO,
                new HashMap<>()
        ).getResponse().getContentAsString();
        final LoggedUserDTO loggedUserDTO = fromJson(jsonResponse, LoggedUserDTO.class);
        assertEquals(JOIN_MASTER_USERNAME, loggedUserDTO.username());
    }

    @Test
    void mustThrowUnauthorizedWhenLoginOrPasswordIsIncorrectly() throws Exception{
        final CredentialsDTO credentialsDTO = new CredentialsDTO(LOGIN_AND_PASSWORD_USER, FAKE_PASSWORD);
        MvcResult result = executeHttpRequest(
                POST,
                LOGIN_CONTEXT_PATH,
                credentialsDTO,
                new HashMap<>()
        );
        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
    }

    @TestConfiguration
    static class MockMvcTestConfiguration {
        @Bean
        public MockMvc mockMvc(WebApplicationContext context) {
            return webAppContextSetup(context).build();
        }
    }
}