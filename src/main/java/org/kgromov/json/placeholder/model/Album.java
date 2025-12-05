package org.kgromov.json.placeholder.model;

/**
 * Represents a album created by a user.
 * This is a managed entity and a child of the User entity.
 *
 * @param id The unique identifier of the album
 * @param title The title of the album
 * @param userId The ID of the user who created the album
 */
public record Album(long id, String title, Long userId) {
}
