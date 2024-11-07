package com.join.testcase.interfaces.controllers;

/**
 * Interface for create operations in a controller (CRUD).
 *
 * @param <D> The type of the response DTO.
 * @param <C> The type of the request DTO for creation.
 */
public interface JoinTestCaseCreateController<D, C> {

    /**
     * Creates a new resource based on the provided DTO.
     *
     * @param createRequestDTO The request DTO containing data for creation.
     * @return The response DTO representing the created resource.
     */
    D create(final C createRequestDTO);
}
