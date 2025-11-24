package org.kgromov.model;

public record Comment(long id, long postId, String name, String email, String body) {
}
