package org.kgromov.json.placeholder.client;

import org.kgromov.json.placeholder.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserClient extends JsonPlaceholderRestClient<User> {

    public UserClient(RestClient restClient) {
        super(restClient);
    }

    @Override
    protected String getUri() {
        return "users";
    }

    @Override
    protected Class<User> getResponseType() {
        return User.class;
    }
}
