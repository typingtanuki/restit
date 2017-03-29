package com.github.typingtanuki.restit.mocks;

import com.github.typingtanuki.restit.model.HttpMethod;

/**
 * @author Clerc Mathias
 */
public class TestResponse {
    private HttpMethod method;
    private String body;
    private String url;

    public TestResponse() {
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
