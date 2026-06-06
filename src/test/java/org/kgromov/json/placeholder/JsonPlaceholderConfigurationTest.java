package org.kgromov.json.placeholder;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.kgromov.json.placeholder.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JsonPlaceholderConfiguration} — verifies all beans are correctly wired,
 * and for {@link JsonPlaceholderServiceProperties} — verifies default and overridden values.
 *
 * <p>{@link ObjectMapperTestConfig} supplies the missing ObjectMapper bean that is normally
 * provided by Spring Boot's JacksonAutoConfiguration but absent in this narrow slice.</p>
 */
@SpringBootTest(classes = {JsonPlaceholderConfiguration.class})
@Import(JsonPlaceholderConfigurationTest.ObjectMapperTestConfig.class)
class JsonPlaceholderConfigurationTest {

    @TestConfiguration
    static class ObjectMapperTestConfig {
        @Bean
        ObjectMapper objectMapper() {
            return JsonMapper.builder().build();
        }
    }

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JsonPlaceholderServiceProperties properties;

    // -------------------------------------------------------------------------
    // Bean presence
    // -------------------------------------------------------------------------

    @Test
    void allClientBeansArePresent() {
        assertThat(context.getBean(PostClient.class)).isNotNull();
        assertThat(context.getBean(UserClient.class)).isNotNull();
        assertThat(context.getBean(CommentClient.class)).isNotNull();
        assertThat(context.getBean(AlbumClient.class)).isNotNull();
        assertThat(context.getBean(PhotoClient.class)).isNotNull();
        assertThat(context.getBean(TodoClient.class)).isNotNull();
    }

    @Test
    void restClientBeanIsPresent() {
        assertThat(context.containsBean("jsonPlaceholderRestClient")).isTrue();
    }

    // -------------------------------------------------------------------------
    // Default property value
    // -------------------------------------------------------------------------

    @Test
    void defaultBaseUrlIsJsonPlaceholder() {
        assertThat(properties.baseUrl()).isEqualTo("https://jsonplaceholder.typicode.com");
    }

    // -------------------------------------------------------------------------
    // Overridden property value — separate context with custom property
    // -------------------------------------------------------------------------

    @Nested
    @SpringBootTest(classes = {JsonPlaceholderConfiguration.class})
    @Import(JsonPlaceholderConfigurationTest.ObjectMapperTestConfig.class)
    @TestPropertySource(properties = "json-placeholder-service.base-url=https://custom.api.com")
    class WithCustomBaseUrl {

        @Autowired
        private JsonPlaceholderServiceProperties properties;

        @Test
        void customBaseUrlIsApplied() {
            assertThat(properties.baseUrl()).isEqualTo("https://custom.api.com");
        }
    }
}