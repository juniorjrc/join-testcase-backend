package com.join.testcase.interfaces.controllers;

import org.springframework.data.domain.Page;

/**
 * Interface for read operations in a controller (CRUD).
 *
 * @param <D> The type of the response DTO.
 */
public interface JoinTestCaseReadController<D> {

    /**
     * Retrieves a page of resources.
     *
     * @param page The page number to be retrieved.
     * @param size The size of the page.
     * @return A page of DTOs representing the found resources.
     */
    Page<D> findAll(final int page, final int size);

    /**
     * Retrieves a specific resource by its ID.
     *
     * @param id The ID of the resource to be retrieved.
     * @return The response DTO representing the found resource.
     */
    D findById(final Long id);
}
