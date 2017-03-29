package com.github.typingtanuki.restit.mocks;

import com.github.typingtanuki.restit.model.Url;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

/**
 * @author Clerc Mathias
 */
public class WebTargetMock implements WebTarget {
    private final Url url;

    public WebTargetMock(Url url) {
        this.url = url;
    }

    @Override
    public Invocation.Builder request() {
        return new InvocationBuilderMock(url);
    }


    private IllegalStateException mockFail() {
        return new IllegalStateException("Method not implemented in mock");
    }

    @Override
    public URI getUri() {
        throw mockFail();
    }

    @Override
    public UriBuilder getUriBuilder() {
        throw mockFail();
    }

    @Override
    public WebTarget path(String path) {
        throw mockFail();
    }

    @Override
    public WebTarget resolveTemplate(String name, Object value) {
        throw mockFail();
    }

    @Override
    public WebTarget resolveTemplate(String name, Object value, boolean encodeSlashInPath) {
        throw mockFail();
    }

    @Override
    public WebTarget resolveTemplateFromEncoded(String name, Object value) {
        throw mockFail();
    }

    @Override
    public WebTarget resolveTemplates(Map<String, Object> templateValues) {
        throw mockFail();
    }

    @Override
    public WebTarget resolveTemplates(Map<String, Object> templateValues, boolean encodeSlashInPath) {
        throw mockFail();
    }

    @Override
    public WebTarget resolveTemplatesFromEncoded(Map<String, Object> templateValues) {
        throw mockFail();
    }

    @Override
    public WebTarget matrixParam(String name, Object... values) {
        throw mockFail();
    }

    @Override
    public WebTarget queryParam(String name, Object... values) {
        throw mockFail();
    }

    @Override
    public Invocation.Builder request(String... acceptedResponseTypes) {
        throw mockFail();
    }

    @Override
    public Invocation.Builder request(MediaType... acceptedResponseTypes) {
        throw mockFail();
    }

    @Override
    public Configuration getConfiguration() {
        throw mockFail();
    }

    @Override
    public WebTarget property(String name, Object value) {
        throw mockFail();
    }

    @Override
    public WebTarget register(Class<?> componentClass) {
        throw mockFail();
    }

    @Override
    public WebTarget register(Class<?> componentClass, int priority) {
        throw mockFail();
    }

    @Override
    public WebTarget register(Class<?> componentClass, Class<?>[] contracts) {
        throw mockFail();
    }

    @Override
    public WebTarget register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
        throw mockFail();
    }

    @Override
    public WebTarget register(Object component) {
        throw mockFail();
    }

    @Override
    public WebTarget register(Object component, int priority) {
        throw mockFail();
    }

    @Override
    public WebTarget register(Object component, Class<?>[] contracts) {
        throw mockFail();
    }

    @Override
    public WebTarget register(Object component, Map<Class<?>, Integer> contracts) {
        throw mockFail();
    }
}
