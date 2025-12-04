package org.kgromov.json.placeholder.model;

/**
 * Represents a blog post created by a user.
 * This is a managed entity and a child of the User entity.
 *
 * @param id The unique identifier of the post
 * @param title The title of the post
 * @param body The content of the post
 * @param userId The ID of the user who created the post
 */
public record Post(long id, String title, String body, Long userId) {
}
