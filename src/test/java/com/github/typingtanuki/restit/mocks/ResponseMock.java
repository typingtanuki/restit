package com.github.typingtanuki.restit.mocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.typingtanuki.restit.model.HttpMethod;
import com.github.typingtanuki.restit.model.Url;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Clerc Mathias
 */
public class ResponseMock extends Response {
    private static ObjectMapper MAPPER = new ObjectMapper();

    private final HttpMethod method;
    private final Url url;
    private final Entity<?> entity;

    public ResponseMock(HttpMethod method, Url url) {
        this(method, url, null);
    }

    public ResponseMock(HttpMethod method, Url url, Entity<?> entity) {
        this.method = method;
        this.url = url;
        this.entity = entity;
    }

    @Override
    public int getStatus() {
        return statusFromUrl(url);
    }

    private int statusFromUrl(Url url) {
        return Integer.parseInt(url.build().split("status=")[1].split("&")[0]);
    }

    @Override
    public <T> T readEntity(Class<T> entityType) {
        if (!entityType.equals(String.class)) {
            throw new IllegalStateException("This mock can only read string entities");
        }

        TestResponse response = new TestResponse();
        response.setUrl(url.build());
        response.setMethod(method);
        if (entity != null) {
            response.setBody(entity.getEntity().toString());
        } else {
            response.setBody(null);
        }
        try {
            String value = MAPPER.writeValueAsString(response);
            return (T) (Object) value;
        } catch (IOException e) {
            throw new IllegalStateException("Could not serialize mock response", e);
        }
    }


    private IllegalStateException mockFail() {
        return new IllegalStateException("Method not implemented in mock");
    }

    @Override
    public StatusType getStatusInfo() {
        throw mockFail();
    }

    @Override
    public Object getEntity() {
        throw mockFail();
    }

    @Override
    public <T> T readEntity(GenericType<T> entityType) {
        throw mockFail();
    }

    @Override
    public <T> T readEntity(Class<T> entityType, Annotation[] annotations) {
        throw mockFail();
    }

    @Override
    public <T> T readEntity(GenericType<T> entityType, Annotation[] annotations) {
        throw mockFail();
    }

    @Override
    public boolean hasEntity() {
        throw mockFail();
    }

    @Override
    public boolean bufferEntity() {
        throw mockFail();
    }

    @Override
    public void close() {
        throw mockFail();
    }

    @Override
    public MediaType getMediaType() {
        throw mockFail();
    }

    @Override
    public Locale getLanguage() {
        throw mockFail();
    }

    @Override
    public int getLength() {
        throw mockFail();
    }

    @Override
    public Set<String> getAllowedMethods() {
        throw mockFail();
    }

    @Override
    public Map<String, NewCookie> getCookies() {
        throw mockFail();
    }

    @Override
    public EntityTag getEntityTag() {
        throw mockFail();
    }

    @Override
    public Date getDate() {
        throw mockFail();
    }

    @Override
    public Date getLastModified() {
        throw mockFail();
    }

    @Override
    public URI getLocation() {
        throw mockFail();
    }

    @Override
    public Set<Link> getLinks() {
        throw mockFail();
    }

    @Override
    public boolean hasLink(String relation) {
        throw mockFail();
    }

    @Override
    public Link getLink(String relation) {
        throw mockFail();
    }

    @Override
    public Link.Builder getLinkBuilder(String relation) {
        throw mockFail();
    }

    @Override
    public MultivaluedMap<String, Object> getMetadata() {
        throw mockFail();
    }

    @Override
    public MultivaluedMap<String, String> getStringHeaders() {
        throw mockFail();
    }

    @Override
    public String getHeaderString(String name) {
        throw mockFail();
    }
}
