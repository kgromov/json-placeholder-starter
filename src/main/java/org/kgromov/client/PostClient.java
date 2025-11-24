package org.kgromov.client;

import org.kgromov.model.Post;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Component
public class PostClient extends JsonPlaceholderRestClient<Post> {

    public PostClient(RestClient restClient) {
        super(restClient);
    }

    public List<Post> getByUserId(Long userId) {
        log.debug("getByUserId: userId={}", userId);
        return this.getAll().stream()
                .filter(post -> Objects.equals(post.userId(), userId))
                .toList();
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
