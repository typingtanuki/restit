package com.github.typingtanuki.restit.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A wrapper on the REST response to simplify assertions
 *
 * @author Clerc Mathias
 */
public class RestResponse {
    private static ObjectMapper MAPPER = new ObjectMapper();

    private final Response response;
    private final Url url;

    public RestResponse(Response response, Url url) {
        this.response = response;
        this.url = url;
    }

    /**
     * Checks that the HTTP status returned is the one we expected
     *
     * @param status the status which was expected
     * @throws AssertionError if the actual status did not match
     */
    public void assertStatus(int status) {
        if (response.getStatusInfo().getStatusCode() != status) {
            assertThat("Wrong REST response status for " + url.build(),
                    response.getStatusInfo().getStatusCode(), is(status));
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
            String body = response.readEntity(String.class);
            return MAPPER.readerFor(clazz).readValue(body);
        } catch (ProcessingException | IllegalStateException | IOException e) {
            throw new AssertionError("Could not extract body from request for " + url.build(), e);
        }
    }
}
