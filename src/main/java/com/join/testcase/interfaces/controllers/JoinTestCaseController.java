package com.join.testcase.interfaces.controllers;

import org.springframework.data.domain.Page;

/**
 * Base interface for CRUD controllers in a RESTful API. This interface defines generic methods
 * for common operations such as reading, updating, and deleting resources.
 *
 * <p>The interface uses two type parameters:
 * <ul>
 *     <li><code>D</code>: represents the DTO (Data Transfer Object) type used for returning data to the client.</li>
 *     <li><code>U</code>: represents the request DTO type used for updating existing resources.</li>
 * </ul>
 *
 * @param <D> The response DTO type, representing the data object returned by the API.
 * @param <U> The update request DTO type, representing the data received for update operations.
 */
public interface JoinTestCaseController<D, U> {

    /**
     * Retrieves a list of all DTO objects.
     *
     * <p>This method fetches all records of the specified type and returns them as a list of <code>D</code> objects.
     *
     * @return a list containing all objects of type <code>D</code>.
     */
    Page<D> findAll(final int page, final int size);

    /**
     * Retrieves a DTO object by its unique identifier.
     *
     * <p>This method locates a resource based on the provided <code>id</code> and returns the corresponding object of type <code>D</code>.
     *
     * @param id The unique identifier of the resource.
     * @return The object of type <code>D</code> that matches the given <code>id</code>.
     */
    D findById(final Long id);

    /**
     * Updates an existing resource with the provided data.
     *
     * <p>This method accepts an object of type <code>U</code> containing the new data and returns the updated resource as type <code>D</code>.
     *
     * @param updateRequestDTO The input object containing the data to update the resource.
     * @return The updated object of type <code>D</code>.
     */
    D update(final Long id, final U updateRequestDTO);

    /**
     * Deletes a resource by its unique identifier.
     *
     * <p>This method removes a resource identified by the given <code>id</code> from the system.
     *
     * @param id The unique identifier of the resource to be deleted.
     */
    void delete(final Long id);
}
