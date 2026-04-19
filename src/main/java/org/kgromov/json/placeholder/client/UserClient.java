package org.kgromov.json.placeholder.client;

import org.kgromov.json.placeholder.model.User;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

/**
 * Client for interacting with the User resources in the JSON Placeholder API.
 * Provides methods to retrieve and manage user data.
 */
public class UserClient extends JsonPlaceholderRestClient<User> {

    /**
     * Constructs a new UserClient with the specified RestClient.
     *
     * @param restClient the RestClient to be used for HTTP requests
     */
    public UserClient(RestClient restClient, ObjectMapper objectMapper) {
        super(restClient, objectMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUri() {
        return "users";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<User> getResponseType() {
        return User.class;
    }
}
