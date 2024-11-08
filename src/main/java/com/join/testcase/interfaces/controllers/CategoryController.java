package com.join.testcase.interfaces.controllers;

public interface CategoryController<D, U> extends
        JoinTestCaseReadController<D>, JoinTestCaseUpdateController<D, U>, JoinTestCaseDeleteController {
}
