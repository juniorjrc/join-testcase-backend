package com.join.testcase.interfaces.controllers;

/**
 * Interface for update operations in a controller (CRUD).
 *
 * @param <D> The type of the response DTO.
 * @param <U> The type of the request DTO for updating.
 */
public interface JoinTestCaseUpdateController<D, U> {

    /**
     * Updates an existing resource based on the provided ID and DTO.
     *
     * @param id The ID of the resource to be updated.
     * @param updateRequestDTO The request DTO containing data for updating.
     * @return The response DTO representing the updated resource.
     */
    D update(final Long id, final U updateRequestDTO);
}
