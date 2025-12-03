package org.kgromov.json.placeholder.client;

import org.kgromov.json.placeholder.model.Post;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * A Client for Post model
 */
public class PostClient extends JsonPlaceholderRestClient<Post> {

    public PostClient(RestClient restClient) {
        super(restClient);
    }

    public List<Post> getUserPosts(long userId) {
        log.debug("getUserPosts: userId={}", userId);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("users/{id}/posts").build(userId))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    protected String getUri() {
        return "posts";
    }

    @Override
    protected Class<Post> getResponseType() {
        return Post.class;
    }
}
