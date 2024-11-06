package com.join.testcase.application.services;

import org.springframework.data.domain.Page;

public interface JoinTestCaseService<D, U> {

    Page<D> findAll(final int page, final int size);

    D findById(final Long id);

    D update(final Long id, final U updateRequestDTO);

    void delete(final Long id);
}
