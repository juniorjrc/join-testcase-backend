package com.join.testcase.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.join.testcase.interfaces.dto.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class JoinTestCaseHttpRequestUtils {

    private static final String HTTP_METHOD_NOT_SUPPORTED = "HTTP method not supported in tests";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    public MvcResult executeHttpRequest(
            JoinTestCaseHttpMethodUtils method,
            String path,
            Object requestBody,
            Map<String, String> queryParams
    ) throws Exception {
        return switch (method) {
            case GET -> executeGet(path, queryParams);
            case POST -> executePost(path, requestBody, queryParams);
            case PUT -> executePut(path, requestBody, queryParams);
            case DELETE -> executeDelete(path, queryParams);
            case PATCH -> executePatch(path, requestBody, queryParams);
            default -> throw new UnsupportedOperationException(HTTP_METHOD_NOT_SUPPORTED);
        };
    }

    private MvcResult executeGet(String path, Map<String, String> queryParams)
            throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(path)
                .queryParams(getQueryParams(queryParams));
        return mockMvc.perform(requestBuilder).andReturn();
    }

    private MvcResult executePost(String path, Object requestBody, Map<String, String> queryParams)
            throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(path)
                .queryParams(getQueryParams(queryParams))
                .content(toJson(requestBody))
                .contentType(APPLICATION_JSON);
        return mockMvc.perform(requestBuilder).andReturn();
    }

    private MvcResult executePut(String path, Object requestBody, Map<String, String> queryParams)
            throws Exception {
        MockHttpServletRequestBuilder requestBuilder = put(path)
                .queryParams(getQueryParams(queryParams))
                .content(toJson(requestBody))
                .contentType(APPLICATION_JSON);
        return mockMvc.perform(requestBuilder).andReturn();
    }

    private MvcResult executeDelete(String path, Map<String, String> queryParams)
            throws Exception {
        MockHttpServletRequestBuilder requestBuilder = delete(path)
                .queryParams(getQueryParams(queryParams));
        return mockMvc.perform(requestBuilder).andReturn();
    }

    private MvcResult executePatch(String path, Object requestBody, Map<String, String> queryParams)
            throws Exception {
        MockHttpServletRequestBuilder requestBuilder = patch(path)
                .queryParams(getQueryParams(queryParams))
                .content(toJson(requestBody))
                .contentType(APPLICATION_JSON);
        return mockMvc.perform(requestBuilder).andReturn();
    }

    private MultiValueMap<String, String> getQueryParams(Map<String, String> queryParams) {
        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        queryParams.forEach(multiValueMap::add);
        return multiValueMap;
    }

    public static String toJson(Object object) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(object);
    }

    public static <T> T fromJson(final String json, final Class<T> clazz) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.fromJson(json, clazz);
    }

    public static <T> Page<T> fromJsonToPage(String json, Class<T> clazz ) {
        Type type = TypeToken.getParameterized(PageResponse.class, clazz).getType();
        PageResponse<T> pageResponse = new Gson().fromJson(json, type);
        int pageNumber = Math.max(pageResponse.getPageNumber(), 0);
        int pageSize = pageResponse.getPageSize() > 0 ? pageResponse.getPageSize() : 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return new PageImpl<>(pageResponse.getContent(), pageable, pageResponse.getTotalElements());
    }
}
