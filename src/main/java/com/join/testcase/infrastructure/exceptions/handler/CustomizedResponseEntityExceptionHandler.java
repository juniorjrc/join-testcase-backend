package com.join.testcase.infrastructure.exceptions.handler;

import com.join.testcase.infrastructure.exceptions.JoinTestCaseException;
import com.join.testcase.infrastructure.exceptions.JoinTestCaseExceptionResponse;
import com.join.testcase.interfaces.enums.ExceptionCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.join.testcase.interfaces.enums.ExceptionCodeEnum.INTERNAL_SERVER_ERROR;
import static java.util.Objects.nonNull;

@Slf4j
@ControllerAdvice
@Primary
public class CustomizedResponseEntityExceptionHandler {

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<JoinTestCaseExceptionResponse> handleByCode(final Throwable throwable) {
        final boolean isCustomException = throwable instanceof JoinTestCaseException;

        final HttpStatus responseHttpStatus = isCustomException ?
                ((JoinTestCaseException) throwable).getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(buildResponse(throwable, isCustomException), responseHttpStatus);
    }

    private JoinTestCaseExceptionResponse buildResponse(
            final Throwable throwable,
            final boolean isCustomException
    ) {
        String code = INTERNAL_SERVER_ERROR.name();
        Map<String, String> errors = new HashMap<>();

        if (isCustomException && nonNull(((JoinTestCaseException) throwable).getCode())) {
            code = ((JoinTestCaseException) throwable).getCode().name();
        }

        if (throwable instanceof MethodArgumentNotValidException) {
            errors = ((MethodArgumentNotValidException) throwable).getBindingResult().getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        }

        return new JoinTestCaseExceptionResponse(
                code,
                throwable.getMessage(),
                nonNull(throwable.getCause()) ? throwable.getCause().getMessage() : StringUtils.EMPTY,
                errors
        );
    }
}
