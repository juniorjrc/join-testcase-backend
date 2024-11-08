package com.join.testcase.infrastructure.exceptions;

import com.join.testcase.interfaces.enums.ExceptionCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JoinTestCaseException extends RuntimeException {

    private final ExceptionCodeEnum code;
    private final HttpStatus status;

    protected JoinTestCaseException(final ExceptionCodeEnum code, final String message, final HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
