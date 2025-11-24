package org.kgromov.client;

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

abstract class JsonPlaceholderRestClient<T> {
    protected final RestClient restClient;
    protected final Logger log;

    JsonPlaceholderRestClient(RestClient restClient) {
        this.restClient = restClient;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public T getById(long id) {
        log.debug("getById({}): id={}", this.getCurrentType().getSimpleName(), id);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path(this.getUri() + "/{id}").build(id))
                .retrieve()
                .body(this.getResponseType());
    }


    public List<T> getAll() {
        log.debug("getAll({})", this.getCurrentType().getSimpleName());
        return List.of(
                restClient.get()
                        .uri(this.getUri())
                        .retrieve()
                        .body(this.getCurrentArrayType())
        );
    }

    public List<T> getAllByQueryParams(Map<String, String> queryParams) {
        var multiValueMap = new LinkedMultiValueMap<String, String>();
        queryParams.forEach(multiValueMap::add);
        return this.getAllByQueryParams(multiValueMap);
    }

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

    @SuppressWarnings("unchecked")
    private Class<T> getCurrentType() {
        return (Class<T>) GenericTypeResolver.resolveTypeArgument(this.getClass(), JsonPlaceholderRestClient.class);
    }

    @SuppressWarnings("unchecked")
    private Class<T[]> getCurrentArrayType() {
        return (Class<T[]>) this.getCurrentType().arrayType();
    }

    protected abstract String getUri();

    protected abstract Class<T> getResponseType();
}
