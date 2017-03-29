package com.github.typingtanuki.restit.mocks;

import com.github.typingtanuki.restit.model.HttpMethod;
import com.github.typingtanuki.restit.model.Url;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.*;
import java.util.Locale;

/**
 * @author Clerc Mathias
 */
public class InvocationBuilderMock implements Invocation.Builder {
    private final Url url;

    public InvocationBuilderMock(Url url) {
        this.url = url;
    }

    @Override
    public Response get() {
        return new ResponseMock(HttpMethod.GET, url);
    }

    @Override
    public Response post(Entity<?> entity) {
        return new ResponseMock(HttpMethod.POST, url, entity);
    }

    @Override
    public Response put(Entity<?> entity) {
        return new ResponseMock(HttpMethod.PUT, url, entity);
    }

    @Override
    public Response delete() {
        return new ResponseMock(HttpMethod.DELETE, url);
    }

    @Override
    public Invocation.Builder accept(String... mediaTypes) {
        return this;
    }

    private IllegalStateException mockFail() {
        return new IllegalStateException("Method not implemented in mock");
    }

    @Override
    public Invocation build(String method) {
        throw mockFail();
    }

    @Override
    public Invocation build(String method, Entity<?> entity) {
        throw mockFail();
    }

    @Override
    public Invocation buildGet() {
        throw mockFail();
    }

    @Override
    public Invocation buildDelete() {
        throw mockFail();
    }

    @Override
    public Invocation buildPost(Entity<?> entity) {
        throw mockFail();
    }

    @Override
    public Invocation buildPut(Entity<?> entity) {
        throw mockFail();
    }

    @Override
    public AsyncInvoker async() {
        throw mockFail();
    }

    @Override
    public Invocation.Builder accept(MediaType... mediaTypes) {
        throw mockFail();
    }

    @Override
    public Invocation.Builder acceptLanguage(Locale... locales) {
        throw mockFail();
    }

    @Override
    public Invocation.Builder acceptLanguage(String... locales) {
        throw mockFail();
    }

    @Override
    public Invocation.Builder acceptEncoding(String... encodings) {
        throw mockFail();
    }

    @Override
    public Invocation.Builder cookie(Cookie cookie) {
        throw mockFail();
    }

    @Override
    public Invocation.Builder cookie(String name, String value) {
        throw mockFail();
    }

    @Override
    public Invocation.Builder cacheControl(CacheControl cacheControl) {
        throw mockFail();
    }

    @Override
    public Invocation.Builder header(String name, Object value) {
        throw mockFail();
    }

    @Override
    public Invocation.Builder headers(MultivaluedMap<String, Object> headers) {
        throw mockFail();
    }

    @Override
    public Invocation.Builder property(String name, Object value) {
        throw mockFail();
    }

    @Override
    public <T> T get(Class<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T get(GenericType<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T put(Entity<?> entity, Class<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T put(Entity<?> entity, GenericType<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T post(Entity<?> entity, Class<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T post(Entity<?> entity, GenericType<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T delete(Class<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T delete(GenericType<T> responseType) {
        throw mockFail();
    }

    @Override
    public Response head() {
        throw mockFail();
    }

    @Override
    public Response options() {
        throw mockFail();
    }

    @Override
    public <T> T options(Class<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T options(GenericType<T> responseType) {
        throw mockFail();
    }

    @Override
    public Response trace() {
        throw mockFail();
    }

    @Override
    public <T> T trace(Class<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T trace(GenericType<T> responseType) {
        throw mockFail();
    }

    @Override
    public Response method(String name) {
        throw mockFail();
    }

    @Override
    public <T> T method(String name, Class<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T method(String name, GenericType<T> responseType) {
        throw mockFail();
    }

    @Override
    public Response method(String name, Entity<?> entity) {
        throw mockFail();
    }

    @Override
    public <T> T method(String name, Entity<?> entity, Class<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T method(String name, Entity<?> entity, GenericType<T> responseType) {
        throw mockFail();
    }
}
