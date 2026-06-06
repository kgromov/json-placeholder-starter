package org.kgromov.json.placeholder.client;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Base class for JSON Placeholder REST client unit tests.
 * Sets up a {@link MockRestServiceServer} bound to a shared {@link RestClient.Builder},
 * so subclasses get a fresh server and builder for every test method.
 */
abstract class AbstractJsonPlaceholderClientTest {

    protected static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    protected MockRestServiceServer mockServer;
    private RestClient.Builder restClientBuilder;

    @BeforeEach
    void setUpServer() {
        restClientBuilder = RestClient.builder().baseUrl(BASE_URL);
        mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
    }

    /**
     * Builds the {@link RestClient} after the mock server has been bound.
     * Subclasses call this inside their own {@code @BeforeEach} to construct the client under test.
     */
    protected RestClient buildRestClient() {
        return restClientBuilder.build();
    }

    // -------------------------------------------------------------------------
    // Shared expectation helpers — keep test bodies concise
    // -------------------------------------------------------------------------

    protected void expectGet(String url, String jsonBody) {
        mockServer.expect(requestTo(BASE_URL + url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(jsonBody, MediaType.APPLICATION_JSON));
    }

    protected void expectPost(String url, String jsonBody) {
        mockServer.expect(requestTo(BASE_URL + url))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(jsonBody, MediaType.APPLICATION_JSON));
    }

    protected void expectPut(String url, String jsonBody) {
        mockServer.expect(requestTo(BASE_URL + url))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess(jsonBody, MediaType.APPLICATION_JSON));
    }

    protected void expectPatch(String url, String jsonBody) {
        mockServer.expect(requestTo(BASE_URL + url))
                .andExpect(method(HttpMethod.PATCH))
                .andRespond(withSuccess(jsonBody, MediaType.APPLICATION_JSON));
    }

    protected void expectDelete(String url) {
        mockServer.expect(requestTo(BASE_URL + url))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());
    }
}