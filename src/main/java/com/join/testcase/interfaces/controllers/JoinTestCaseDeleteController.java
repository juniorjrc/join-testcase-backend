package com.join.testcase.interfaces.controllers;

/**
 * Interface for delete operations in a controller (CRUD).
 */
public interface JoinTestCaseDeleteController {

    /**
     * Deletes a specific resource by its ID.
     *
     * @param id The ID of the resource to be deleted.
     */
    void delete(final Long id);
}
