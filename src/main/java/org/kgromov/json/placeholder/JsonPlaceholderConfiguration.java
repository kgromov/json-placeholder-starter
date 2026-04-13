package org.kgromov.json.placeholder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kgromov.json.placeholder.client.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

/**
 * Configuration class for the JSON Placeholder API client.
 *
 * <p>This class provides Spring configuration for all JSON Placeholder API clients
 * and configures the underlying {@link RestClient} with the appropriate base URL.</p>
 *
 * <p>It enables configuration properties from {@link JsonPlaceholderServiceProperties}
 * and automatically configures the following beans:</p>
 * <ul>
 *   <li>A {@link RestClient} bean named "jsonPlaceholderRestClient"</li>
 *   <li>A {@link PostClient} for interacting with posts</li>
 *   <li>A {@link UserClient} for interacting with users</li>
 *   <li>A {@link CommentClient} for interacting with comments</li>
 * </ul>
 */
@EnableConfigurationProperties(JsonPlaceholderServiceProperties.class)
@AutoConfiguration
public class JsonPlaceholderConfiguration {

    /**
     * Creates a new instance of JsonPlaceholderConfiguration.
     * This constructor is used by Spring to create the configuration bean.
     */
    public JsonPlaceholderConfiguration() {
        // Default constructor for Spring
    }

    /**
     * Creates and configures a RestClient for the JSON Placeholder API.
     *
     * @param properties the configuration properties containing the base URL
     * @return a configured RestClient instance
     */
    @Bean("jsonPlaceholderRestClient")
    RestClient restClient(JsonPlaceholderServiceProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.baseUrl())
                .build();
    }

    /**
     * Creates a UserClient instance.
     *
     * @param restClient the RestClient to be used by the UserClient
     * @return a new UserClient instance
     */
    @Bean
    UserClient userClient(RestClient restClient,ObjectMapper objectMapper) {
        return new UserClient(restClient, objectMapper);
    }

    /**
     * Creates a TodoClient instance.
     *
     * @param restClient the RestClient to be used by the TodoClient
     * @return a new TodoClient instance
     */
    @Bean
    TodoClient todoClient(RestClient restClient,ObjectMapper objectMapper) {
        return new TodoClient(restClient, objectMapper);
    }

    /**
     * Creates a PostClient instance.
     *
     * @param restClient the RestClient to be used by the PostClient
     * @return a new PostClient instance
     */
    @Bean
    PostClient postClient(RestClient restClient,ObjectMapper objectMapper) {
        return new PostClient(restClient, objectMapper);
    }

    /**
     * Creates a CommentClient instance.
     *
     * @param restClient the RestClient to be used by the CommentClient
     * @return a new CommentClient instance
     */
    @Bean
    CommentClient commentClient(RestClient restClient,ObjectMapper objectMapper) {
        return new CommentClient(restClient, objectMapper);
    }

    /**
     * Creates a AlbumClient instance.
     *
     * @param restClient the RestClient to be used by the AlbumClient
     * @return a new AlbumClient instance
     */
    @Bean
    AlbumClient albumClient(RestClient restClient,ObjectMapper objectMapper) {
        return new AlbumClient(restClient, objectMapper);
    }

    /**
     * Creates a PhotoClient instance.
     *
     * @param restClient the RestClient to be used by the PhotoClient
     * @return a new PhotoClient instance
     */
    @Bean
    PhotoClient photoClient(RestClient restClient,ObjectMapper objectMapper) {
        return new PhotoClient(restClient, objectMapper);
    }
}
