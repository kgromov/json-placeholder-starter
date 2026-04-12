package org.kgromov.json.placeholder.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kgromov.json.placeholder.model.Album;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Client for interacting with the Album resources in the JSON Placeholder API.
 * Provides methods to retrieve albums and albums by user.
 */
public class AlbumClient extends JsonPlaceholderRestClient<Album> {

    /**
     * Constructs a new AlbumClient with the specified RestClient.
     *
     * @param restClient the RestClient to be used for HTTP requests
     * @param objectMapper the ObjectMapper to be used for JSON serialization/deserialization
     */
    public AlbumClient(RestClient restClient, ObjectMapper objectMapper) {
        super(restClient, objectMapper);
    }

    /**
     * Retrieves all albums for a specific user.
     *
     * @param userId the ID of the user to retrieve albums for
     * @return a list of albums associated with the specified user
     */
    public List<Album> getUserAlbums(long userId) {
        log.debug("getUserAlbums: userId={}", userId);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("users/{userId}/albums").build(userId))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUri() {
        return "albums";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Album> getResponseType() {
        return Album.class;
    }
}
