package org.kgromov.json.placeholder;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Configuration properties for JSON Placeholder API
 * For now only baseUrl property is available
 */
@ConfigurationProperties("json-placeholder-service")
public record JsonPlaceholderServiceProperties(
        @DefaultValue("https://jsonplaceholder.typicode.com")
        String baseUrl
) {

}
