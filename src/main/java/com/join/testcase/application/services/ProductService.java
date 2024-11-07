package com.join.testcase.application.services;

public interface ProductService<D, C, U> extends
        JoinTestCaseCreateService<D, C>, JoinTestCaseReadService<D>, JoinTestCaseUpdateService<D, U>, JoinTestCaseDeleteService {
}
