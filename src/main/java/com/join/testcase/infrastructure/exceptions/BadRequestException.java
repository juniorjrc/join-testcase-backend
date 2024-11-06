package com.join.testcase.infrastructure.exceptions;

import com.join.testcase.interfaces.enums.ExceptionCodeEnum;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class BadRequestException extends JoinTestCaseException{

    public BadRequestException(final String message) {
        super(ExceptionCodeEnum.BAD_REQUEST, message, BAD_REQUEST);
    }
}
