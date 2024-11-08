package com.join.testcase.interfaces.controllers;

public interface ProductController<D, U, C> extends
        JoinTestCaseCreateController<D, C>, JoinTestCaseReadController<D>, JoinTestCaseUpdateController<D, U>, JoinTestCaseDeleteController{
}
