package org.kgromov.json.placeholder.model;

/**
 * Represents geographic coordinates (latitude and longitude).
 * This is a value object used within the Address model.
 *
 * @param lat The latitude coordinate as a string
 * @param lng The longitude coordinate as a string
 */
public record Geo(
        String lat,
        String lng
) {}
