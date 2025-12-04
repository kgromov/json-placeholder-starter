package org.kgromov.json.placeholder.client;

import org.kgromov.json.placeholder.model.User;
import org.springframework.web.client.RestClient;

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
    public UserClient(RestClient restClient) {
        super(restClient);
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
