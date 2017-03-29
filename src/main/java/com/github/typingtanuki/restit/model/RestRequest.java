package com.github.typingtanuki.restit.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * A wrapper on the REST request
 * <p>
 * To add a body (POST and PUT requests only), call the method withBody on the instance.
 *
 * @author Clerc Mathias
 */
public class RestRequest {
    private static ObjectMapper MAPPER = new ObjectMapper();

    private final HttpMethod method;
    private final Url url;
    private Object entity;

    /**
     * Creates a basic request
     *
     * @param method the method to use for the REST calls
     * @param url    the relative url to connect to
     */
    public RestRequest(HttpMethod method, Url url) {
        this.method = method;
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Url getUrl() {
        return url;
    }

    public Object getEntity() {
        return entity;
    }

    /**
     * Set the entity to send with the request.
     * <p>
     * Only works on POST and PUT requests.
     *
     * @param entity The entity to send
     * @return this instance with the field set
     * @throws IllegalStateException If entity is being set on an unsupported type of http method
     */
    public RestRequest withEntity(Object entity) {
        switch (method) {
            case POST:
            case PUT:
                this.entity = entity;
                return this;
        }
        throw new IllegalStateException("Can only set an entity on POST and PUT requests. Current " + method);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(method + " " + url.build());
        if (this.entity != null) {
            try {
                out.append(" (entity: ").append(MAPPER.writer().writeValueAsString(entity)).append(")");
            } catch (IOException e) {
                out.append(" (entity: un-serializable)");
            }
        }
        return out.toString();
    }
}
