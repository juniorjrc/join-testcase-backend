package com.join.testcase.application.services;

import org.springframework.data.domain.Page;

public interface JoinTestCaseReadService<D> {

    Page<D> findAll(final int page, final int size);

    D findById(final Long id);
}
