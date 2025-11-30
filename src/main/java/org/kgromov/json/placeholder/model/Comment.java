package org.kgromov.json.placeholder.model;

public record Comment(long id, long postId, String name, String email, String body) {
}
