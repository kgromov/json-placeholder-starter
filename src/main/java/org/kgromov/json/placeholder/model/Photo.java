package org.kgromov.json.placeholder.model;

/**
 * Represents a photo on an album.
 * This is a managed entity and a child of the Album entity.
 *
 * @param id The unique identifier of the photo
 * @param title The title of the photo
 * @param url The URL of the photo
 * @param thumbnailUrl The URL of the thumbnail of the photo
 * @param albumId The ID of the album this photo belongs to
 */
public record Photo(long id, String title, String url, String thumbnailUrl, long albumId) {
}
