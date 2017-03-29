package com.github.typingtanuki.restit.model.internal;

import com.github.typingtanuki.restit.model.RestRequest;
import com.github.typingtanuki.restit.model.RestResponse;

import javax.ws.rs.core.Response;
import java.util.Date;

/**
 * A builder for the REST responses (internal use only)
 *
 * @author Clerc Mathias
 */
public final class RestResponseBuilder {
    private final RestRequest request;
    private final long requestTime;

    public RestResponseBuilder(RestRequest request) {
        this.request = request;
        this.requestTime = new Date().getTime();
    }

    public RestResponse build(Response response) {
        return new RestResponse(request, requestTime, response, new Date().getTime());
    }
}
