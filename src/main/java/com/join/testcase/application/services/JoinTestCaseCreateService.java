package com.join.testcase.application.services;

public interface JoinTestCaseCreateService <D, C> {

    D create(final C createRequestDTO);
}
