package org.kgromov.json.placeholder.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kgromov.json.placeholder.model.Comment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Client for interacting with the Comment resources in the JSON Placeholder API.
 * Provides methods to retrieve comments and comments by post.
 */
public class CommentClient extends JsonPlaceholderRestClient<Comment> {

    /**
     * Constructs a new CommentClient with the specified RestClient.
     *
     * @param restClient the RestClient to be used for HTTP requests
     */
    public CommentClient(RestClient restClient, ObjectMapper objectMapper) {
        super(restClient, objectMapper);
    }

    /**
     * Retrieves all comments for a specific post.
     *
     * @param postId the ID of the post to retrieve comments for
     * @return a list of comments associated with the specified post
     */
    public List<Comment> getPostComments(long postId) {
        log.debug("getCommentsByPostId: postId={}", postId);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("posts/{postId}/comments").build(postId))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUri() {
        return "comments";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Comment> getResponseType() {
        return Comment.class;
    }
}
