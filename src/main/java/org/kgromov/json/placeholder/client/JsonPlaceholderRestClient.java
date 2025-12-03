package org.kgromov.json.placeholder.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

/**
 * Base class for REST clients that interact with the JSON Placeholder API.
 * Implements CRUD operations for the entity type T.
 * As well as pagination and filtering via query parameters support.
 *
 * @param <T> the type of the entity being managed by the client
 */
abstract class JsonPlaceholderRestClient<T> {
    protected final RestClient restClient;
    protected final Logger log;

    JsonPlaceholderRestClient(RestClient restClient) {
        this.restClient = restClient;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * Defined as long to be compatible with the JSON Placeholder API.
     * @return the entity with the specified ID
     */
    public T getById(long id) {
        log.debug("getById({}): id={}", this.getCurrentType().getSimpleName(), id);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path(this.getUri() + "/{id}").build(id))
                .retrieve()
                .body(this.getResponseType());
    }

    /**
     * Retrieves all entities.
     *
     * @return a list of all entities
     */
    public List<T> getAll() {
        log.debug("getAll({})", this.getCurrentType().getSimpleName());
        return List.of(
                restClient.get()
                        .uri(this.getUri())
                        .retrieve()
                        .body(this.getCurrentArrayType())
        );
    }

    /**
     * Retrieves all entities filtered by query parameters.
     *
     * @param queryParams the query parameters to filter by
     * with single value for each key.
     * @return a list of entities filtered by the specified query parameters
     */
    public List<T> getAllByQueryParams(Map<String, String> queryParams) {
        var multiValueMap = new LinkedMultiValueMap<String, String>();
        queryParams.forEach(multiValueMap::add);
        return this.getAllByQueryParams(multiValueMap);
    }

    /**
     * Retrieves all entities filtered by query parameters.
     *
     * @param queryParams the query parameters to filter by
     * with multiple values for each key.
     * @return a list of entities filtered by the specified query parameters
     */
    public List<T> getAllByQueryParams(MultiValueMap<String, String> queryParams) {
        log.debug("getAllByQueryParams({}): queryParams:{}", this.getCurrentType().getSimpleName(), queryParams);
        return List.of(
                restClient.get()
                        .uri(uriBuilder -> uriBuilder.path(this.getUri())
                                .queryParams(queryParams)
                                .build()
                        )
                        .retrieve()
                        .body(this.getCurrentArrayType())
        );
    }

    /**
     * Retrieves a page of entities.
     * Returns a list of entities in the specified page.
     * Since JSON Placeholder API does not return the total number of pages,
     * return type is List<T>.
     *
     * @param pageRequest the page request
     * @return a list of entities in the specified page
     */
    public List<T> getPage(Pageable pageRequest) {
        Sort sort = pageRequest.getSort();
        log.debug("getPage({}): [page ={}, size={}, sort by: {}",
                this.getCurrentType().getSimpleName(), pageRequest.getPageNumber(), pageRequest.getPageSize(), sort);
        var sortProperties = sort.stream().map(Sort.Order::getProperty).collect(joining(","));
        var direction = sort.stream().map(Sort.Order::getDirection).findFirst();
        return List.of(
                restClient.get()
                        .uri(uriBuilder -> uriBuilder.path(this.getUri())
                                .queryParamIfPresent("_start", ofNullable(pageRequest.getOffset()))
                                .queryParamIfPresent("_page", ofNullable(pageRequest.getPageNumber()))
                                .queryParam("_limit", pageRequest.getPageSize())
                                .queryParam("_sort", sortProperties)
                                .queryParamIfPresent("_order", direction)
                                .build()
                        )
                        .retrieve()
                        .body(this.getCurrentArrayType())
        );
    }

    /**
     * Creates a new entity.
     *
     * @param entity the entity to create
     * @return the created entity
     */
    public T create(T entity) {
        log.debug("create({}): entity={}", this.getCurrentType().getSimpleName(), entity);
        return restClient.post()
                .uri(this.getUri())
                .body(entity)
                .retrieve()
                .body(this.getResponseType());
    }

    /**
     * Updates an existing entity.
     *
     * @param id the ID of the entity to update
     * @param entity the entity to update
     * @return the updated entity
     */
    public T update(long id, T entity) {
        log.debug("update({}): entity={}", this.getCurrentType().getSimpleName(), entity);
        return restClient.put()
                .uri(uriBuilder -> uriBuilder.path(this.getUri() + "/{id}").build(id))
                .body(entity)
                .retrieve()
                .body(this.getResponseType());
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id the ID of the entity to delete
     */
    public void delete(long id) {
        log.debug("delete({}): id={}", this.getCurrentType().getSimpleName(), id);
        restClient.delete()
                .uri(uriBuilder -> uriBuilder.path(this.getUri() + "/{id}").build(id))
                .retrieve()
                .toBodilessEntity();
    }

    /**
     * Returns the current type of the entity being managed by the client.
     *
     * @return the current type of the entity
     */
    @SuppressWarnings("unchecked")
    private Class<T> getCurrentType() {
        return (Class<T>) GenericTypeResolver.resolveTypeArgument(this.getClass(), JsonPlaceholderRestClient.class);
    }

    /**
     * Helper method to returns the current array type of the entity being managed by the client.
     *
     * @return the current array type of the entity
     */
    @SuppressWarnings("unchecked")
    private Class<T[]> getCurrentArrayType() {
        return (Class<T[]>) this.getCurrentType().arrayType();
    }

    /**
     * 1-level uri path for the entity being managed by the client.
     * For example, for the Post entity it is "posts".
     *
     * @return  uri path for the entity
     */
    protected abstract String getUri();

    /**
     * Returns the response type of the entity being managed by the client.
     *
     * @return the response type of the entity
     */
    protected abstract Class<T> getResponseType();
}
