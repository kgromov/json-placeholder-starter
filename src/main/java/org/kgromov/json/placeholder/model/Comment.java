package org.kgromov.json.placeholder.model;

/**
 * Represents a comment on a post.
 * This is a managed entity and a child of the Post entity.
 *
 * @param id The unique identifier of the comment
 * @param postId The ID of the post this comment belongs to
 * @param name The name of the comment author
 * @param email The email of the comment author
 * @param body The content of the comment
 */
public record Comment(long id, long postId, String name, String email, String body) {
}
