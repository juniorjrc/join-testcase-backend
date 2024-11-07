package com.join.testcase.application.services;

public interface JoinTestCaseUpdateService<D, U> {

    D update(final Long id, final U updateRequestDTO);
}
