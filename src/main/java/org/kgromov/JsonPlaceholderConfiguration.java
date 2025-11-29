package org.kgromov;

import org.kgromov.client.CommentClient;
import org.kgromov.client.PostClient;
import org.kgromov.client.UserClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@EnableConfigurationProperties(JsonPlaceholderServiceProperties.class)
@AutoConfiguration
public class JsonPlaceholderConfiguration {

    @Bean("jsonPlaceholderRestClient")
    RestClient restClient(JsonPlaceholderServiceProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.baseUrl())
                .build();
    }

    @Bean
    PostClient postClient(RestClient restClient) {
        return new PostClient(restClient);
    }

    @Bean
    UserClient userClient(RestClient restClient) {
        return new UserClient(restClient);
    }

    @Bean
    CommentClient commentClient(RestClient restClient) {
        return new CommentClient(restClient);
    }
}
