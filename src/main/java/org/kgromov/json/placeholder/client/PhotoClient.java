package org.kgromov.json.placeholder.client;

import org.kgromov.json.placeholder.model.Photo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Client for interacting with the Photo resources in the JSON Placeholder API.
 * Provides methods to retrieve photos and photos by album.
 */
public class PhotoClient extends JsonPlaceholderRestClient<Photo> {

    /**
     * Constructs a new CommentClient with the specified RestClient.
     *
     * @param restClient the RestClient to be used for HTTP requests
     */
    public PhotoClient(RestClient restClient) {
        super(restClient);
    }

    /**
     * Retrieves all photos for a specific album.
     *
     * @param albumId the ID of the album to retrieve photos for
     * @return a list of photos associated with the specified album
     */
    public List<Photo> getPostComments(long albumId) {
        log.debug("getPhotosByAlbumId: postId={}", albumId);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("albums/{albumId}/photos").build(albumId))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUri() {
        return "photos";
    }

    @Override
    protected Class<Photo> getResponseType() {
        return Photo.class;
    }
}
