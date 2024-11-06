package com.join.testcase.infrastructure.exceptions;

import com.join.testcase.interfaces.enums.ExceptionCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ResponseStatus(UNAUTHORIZED)
public class InvalidJwtAuthenticationException extends JoinTestCaseException {

	public InvalidJwtAuthenticationException(final String message) {
		super(ExceptionCodeEnum.INVALID_JWT_TOKEN, message, UNAUTHORIZED);
	}
}
