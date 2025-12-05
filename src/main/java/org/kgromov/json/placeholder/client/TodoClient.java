package org.kgromov.json.placeholder.client;

import org.kgromov.json.placeholder.model.Todo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

/**F
 * Client for interacting with the Todo resources in the JSON Placeholder API.
 * Provides methods to retrieve todos and todos by user.
 */
public class TodoClient extends JsonPlaceholderRestClient<Todo> {

    /**
     * Constructs a new PostClient with the specified RestClient.
     *
     * @param restClient the RestClient to be used for HTTP requests
     */
    public TodoClient(RestClient restClient) {
        super(restClient);
    }

    /**
     * Retrieves all todos for a specific user.
     *
     * @param userId the ID of the user to retrieve todos for
     * @return a list of todos associated with the specified user
     */
    public List<Todo> getUserPosts(long userId) {
        log.debug("getUserTodos: userId={}", userId);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("users/{userId}/todos").build(userId))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUri() {
        return "todos";
    }

    @Override
    protected Class<Todo> getResponseType() {
        return Todo.class;
    }
}
