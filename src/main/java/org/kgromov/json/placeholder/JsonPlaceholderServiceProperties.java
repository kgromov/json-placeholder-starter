package org.kgromov.json.placeholder;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Configuration properties for the JSON Placeholder API client.
 * This class is used to configure the base URL for the JSON Placeholder API.
 *
 * @param baseUrl The base URL for the JSON Placeholder API. Defaults to "https://jsonplaceholder.typicode.com"
 */
@ConfigurationProperties("json-placeholder-service")
public record JsonPlaceholderServiceProperties(
        @DefaultValue("https://jsonplaceholder.typicode.com")
        String baseUrl
) {
}
