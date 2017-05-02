package com.github.typingtanuki.restit.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

/**
 * A wrapper on the REST response to simplify assertions
 *
 * @author Clerc Mathias
 */
public final class RestResponse {
    private static ObjectMapper MAPPER = new ObjectMapper();

    private final RestRequest request;
    private final long requestTime;
    private final Response response;
    private final long responseTime;

    private final Object ENTITY_LOCK = new Object[0];
    private String cachedEntity;

    public RestResponse(RestRequest request, long requestTime, Response response, long responseTime) {
        this.request = request;
        this.requestTime = requestTime;
        this.response = response;
        this.responseTime = responseTime;
    }

    /**
     * Checks that the HTTP status returned is the one we expected
     *
     * @param status the status which was expected
     * @throws AssertionError if the actual status did not match
     */
    public void assertStatus(int status) {
        if (response.getStatus() != status) {
            assertThat("Wrong REST response status for " + this,
                    response.getStatus(), is(status));
        }
    }

    /**
     * Check that the reply was done within the specified amount of time
     *
     * @param duration the maximum expected duration
     * @throws AssertionError if the response took too much time to come
     */
    public void assertWithin(long duration) {
        long actualDuration = responseTime - requestTime;
        if (actualDuration > duration) {
            assertThat("REST endpoint took too long to respond for " + this,
                    actualDuration, lessThan(duration));
        }
    }

    /**
     * Deserialize the entity from the response to the specified class
     *
     * @param clazz the class to deserialize to
     * @return the deserialized entity
     * @throws AssertionError if the entity could not be deserialized
     */
    public <T> T getEntity(Class<? extends T> clazz) {
        try {
            String body = stringEntity();
            return MAPPER.readerFor(clazz).readValue(body);
        } catch (ProcessingException | IllegalStateException | IOException e) {
            throw new AssertionError("Could not extract body from request for " + this, e);
        }
    }

    /**
     * The entity can only be read once
     *
     * @return the entity as a string
     */
    private String stringEntity() {
        synchronized (ENTITY_LOCK) {
            if (cachedEntity == null) {
                cachedEntity = response.readEntity(String.class);
            }
            return cachedEntity;
        }
    }

    /**
     * @return The timestamp at which the request was made
     */
    public long getRequestTime() {
        return requestTime;
    }

    /**
     * @return The timestamp at which the response came
     */
    public long getResponseTime() {
        return responseTime;
    }

    /**
     * @return The request matching this response
     */
    public RestRequest getRequest() {
        return request;
    }

    /**
     * @return The jaxrs response from the server
     */
    public Response getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "( " + request + " → " + response.getStatus() + " " + stringEntity() + " )";
    }
}
