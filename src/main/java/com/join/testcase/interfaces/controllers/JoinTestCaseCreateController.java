package com.join.testcase.interfaces.controllers;

/**
 * Interface for controllers that handle resource creation operations in a RESTful API.
 * This interface defines the method for creating new resources.
 *
 * <p>The interface uses two type parameters:
 * <ul>
 *     <li><code>D</code>: represents the DTO (Data Transfer Object) type used for returning the created resource.</li>
 *     <li><code>C</code>: represents the request DTO type used for creating new resources.</li>
 * </ul>
 *
 * @param <D> The response DTO type, representing the data object returned after creation.
 * @param <C> The create request DTO type, representing the data received for create operations.
 */
public interface JoinTestCaseCreateController<D, C> {

    /**
     * Creates a new resource from an input object.
     *
     * <p>This method receives an object of type <code>C</code> with the data needed to create a new resource
     * and returns the newly created resource as type <code>D</code>.
     *
     * @param createRequestDTO The input object with the data to create the resource.
     * @return The newly created object of type <code>D</code>.
     */
    D create(final C createRequestDTO);
}
