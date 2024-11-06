package com.join.testcase.infrastructure.exceptions;

import com.join.testcase.interfaces.enums.ExceptionCodeEnum;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class NotFoundException extends JoinTestCaseException {

    public NotFoundException(final String message) {
        super(ExceptionCodeEnum.NOT_FOUND, message, NOT_FOUND);
    }
}
