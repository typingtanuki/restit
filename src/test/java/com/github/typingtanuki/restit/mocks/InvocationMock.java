package com.github.typingtanuki.restit.mocks;

import com.github.typingtanuki.restit.model.HttpMethod;
import com.github.typingtanuki.restit.model.Url;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.concurrent.Future;

import static com.github.typingtanuki.restit.mocks.InvocationBuilderMock.mockFail;

/**
 * @author Clerc Mathias
 */
public class InvocationMock implements Invocation {
    private final Url url;
    private final String method;
    private Entity<?> entity;

    public InvocationMock(String method, Url url, Entity<?> entity) {
        this.method = method;
        this.url = url;
        this.entity = entity;
    }

    @Override
    public Response invoke() {
        return new ResponseMock(HttpMethod.valueOf(method), url, entity);
    }

    @Override
    public Invocation property(String name, Object value) {
        throw mockFail();
    }

    @Override
    public <T> T invoke(Class<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> T invoke(GenericType<T> responseType) {
        throw mockFail();
    }

    @Override
    public Future<Response> submit() {
        throw mockFail();
    }

    @Override
    public <T> Future<T> submit(Class<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> Future<T> submit(GenericType<T> responseType) {
        throw mockFail();
    }

    @Override
    public <T> Future<T> submit(InvocationCallback<T> callback) {
        throw mockFail();
    }
}
