package org.kgromov.json.placeholder.model;

/**
 * Post model - managed entity, User child entity
 */
public record Post(long id, String title, String body, Long userId) {
}
