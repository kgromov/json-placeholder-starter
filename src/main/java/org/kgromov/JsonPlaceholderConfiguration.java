package org.kgromov;

import org.kgromov.client.CommentClient;
import org.kgromov.client.PostClient;
import org.kgromov.client.UserClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
public class JsonPlaceholderConfiguration {

    @Bean("jsonPlaceholderRestClient")
    RestClient restClient(@Value("spring.json.placeholder.base-url")
                          @DefaultValue("https://jsonplaceholder.typicode.com") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
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
