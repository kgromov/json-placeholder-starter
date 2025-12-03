package org.kgromov.json.placeholder.model;

/**
 * Post model - managed entity, Post child entity
 */
public record Comment(long id, long postId, String name, String email, String body) {
}
