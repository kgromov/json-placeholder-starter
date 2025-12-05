package org.kgromov.json.placeholder.model;

/**
 * Represents a todo item created by a user.
 * This is a managed entity and a child of the User entity.
 *
 * @param id The unique identifier of the todo item
 * @param title The title of the todo item
 * @param completed Whether item is completed
 * @param userId The ID of the user who created the item
 */
public record Todo(long id, String title, boolean completed, Long userId) {
}
