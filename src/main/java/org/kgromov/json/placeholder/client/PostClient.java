package org.kgromov.json.placeholder.client;

import org.kgromov.json.placeholder.model.Post;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PostClient extends JsonPlaceholderRestClient<Post> {

    public PostClient(RestClient restClient) {
        super(restClient);
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
