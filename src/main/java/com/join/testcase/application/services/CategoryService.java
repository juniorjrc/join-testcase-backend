package com.join.testcase.application.services;

public interface CategoryService<D, U>
        extends JoinTestCaseReadService<D>, JoinTestCaseUpdateService<D, U>, JoinTestCaseDeleteService {
}
