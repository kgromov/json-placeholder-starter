package org.kgromov.json.placeholder.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kgromov.json.placeholder.model.Post;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Client for interacting with the Post resources in the JSON Placeholder API.
 * Provides methods to retrieve posts and posts by user.
 */
public class PostClient extends JsonPlaceholderRestClient<Post> {

    /**
     * Constructs a new PostClient with the specified RestClient.
     *
     * @param restClient the RestClient to be used for HTTP requests
     */
    public PostClient(RestClient restClient, ObjectMapper objectMapper) {
        super(restClient, objectMapper);
    }

    /**
     * Retrieves all posts for a specific user.
     *
     * @param userId the ID of the user to retrieve posts for
     * @return a list of posts associated with the specified user
     */
    public List<Post> getUserPosts(long userId) {
        log.debug("getUserPosts: userId={}", userId);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("users/{userId}/posts").build(userId))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUri() {
        return "posts";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Post> getResponseType() {
        return Post.class;
    }
}
