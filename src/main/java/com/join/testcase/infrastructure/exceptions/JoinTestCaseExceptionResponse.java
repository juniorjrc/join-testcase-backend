package com.join.testcase.infrastructure.exceptions;

import java.util.Map;

public record JoinTestCaseExceptionResponse(
        String code,
        String message,
        String details,
        Map<String, String> errors
){}
