package org.kgromov.json.placeholder;

import org.kgromov.json.placeholder.client.CommentClient;
import org.kgromov.json.placeholder.client.PostClient;
import org.kgromov.json.placeholder.client.UserClient;
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
     * Creates a PostClient instance.
     * 
     * @param restClient the RestClient to be used by the PostClient
     * @return a new PostClient instance
     */
    @Bean
    PostClient postClient(RestClient restClient) {
        return new PostClient(restClient);
    }

    /**
     * Creates a UserClient instance.
     * 
     * @param restClient the RestClient to be used by the UserClient
     * @return a new UserClient instance
     */
    @Bean
    UserClient userClient(RestClient restClient) {
        return new UserClient(restClient);
    }

    /**
     * Creates a CommentClient instance.
     * 
     * @param restClient the RestClient to be used by the CommentClient
     * @return a new CommentClient instance
     */
    @Bean
    CommentClient commentClient(RestClient restClient) {
        return new CommentClient(restClient);
    }
}
