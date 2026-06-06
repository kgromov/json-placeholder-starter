package org.kgromov.json.placeholder.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kgromov.json.placeholder.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserClientTest extends AbstractJsonPlaceholderClientTest {

    private UserClient userClient;

    @BeforeEach
    void setUp() {
        userClient = new UserClient(buildRestClient(), null);
    }

    @Test
    void getById_returnsUser_withNestedObjects() {
        expectGet("/users/1", """
                {"id":1,"name":"John Doe","username":"johnd","email":"john@example.com",
                 "address":{"street":"Main St","suite":"Apt 1","city":"Springfield","zipcode":"12345",
                            "geo":{"lat":"40.7","lng":"-74.0"}},
                 "phone":"555-1234","website":"johndoe.com",
                 "company":{"name":"Acme","catchPhrase":"We do it","bs":"synergy"}}
                """);

        User user = userClient.getById(1L);

        assertThat(user.id()).isEqualTo(1L);
        assertThat(user.name()).isEqualTo("John Doe");
        assertThat(user.address().city()).isEqualTo("Springfield");
        assertThat(user.address().geo().lat()).isEqualTo("40.7");
        assertThat(user.company().name()).isEqualTo("Acme");
        mockServer.verify();
    }

    @Test
    void getAll_returnsUsers() {
        expectGet("/users", """
                [{"id":1,"name":"Alice","username":"alice","email":"alice@example.com",
                  "address":null,"phone":null,"website":null,"company":null},
                 {"id":2,"name":"Bob","username":"bob","email":"bob@example.com",
                  "address":null,"phone":null,"website":null,"company":null}]
                """);

        List<User> users = userClient.getAll();

        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::name).containsExactly("Alice", "Bob");
        mockServer.verify();
    }

    @Test
    void create_returnsCreatedUser() {
        expectPost("/users", """
                {"id":11,"name":"Charlie","username":"charlie","email":"charlie@example.com",
                 "address":null,"phone":null,"website":null,"company":null}
                """);

        var newUser = new User(0, "Charlie", "charlie", "charlie@example.com", null, null, null, null);
        User created = userClient.create(newUser);

        assertThat(created.id()).isEqualTo(11L);
        assertThat(created.name()).isEqualTo("Charlie");
        mockServer.verify();
    }

    @Test
    void delete_sendsDeleteRequest() {
        expectDelete("/users/1");

        userClient.delete(1L);

        mockServer.verify();
    }
}