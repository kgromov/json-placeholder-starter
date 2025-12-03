package org.kgromov.json.placeholder.client;

import org.kgromov.json.placeholder.model.Comment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * A Client for Comment model
 */
public class CommentClient extends JsonPlaceholderRestClient<Comment> {

    public CommentClient(RestClient restClient) {
        super(restClient);
    }

    public List<Comment> getPostComments(long postId) {
        log.debug("getCommentsByPostId: postId={}", postId);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("posts/{id}/comments").build(postId))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    protected String getUri() {
        return "comments";
    }

    @Override
    protected Class<Comment> getResponseType() {
        return Comment.class;
    }
}
