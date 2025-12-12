package org.kgromov.json.placeholder.model;

/**
 * Represents a user in the system.
 * This is a managed entity that can create posts and comments.
 *
 * @param id The unique identifier of the user
 * @param name The full name of the user
 * @param username The username of the user
 * @param email The email address of the user
 * @param address The physical address of the user
 * @param phone The contact phone number of the user
 * @param website The personal website or blog URL of the user
 * @param company The company information of the user
 */
public record User(
        long id,
        String name,
        String username,
        String email,
        Address address,
        String phone,
        String website,
        Company company
) {
}
