package com.github.typingtanuki.restit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.typingtanuki.restit.model.RestRequest;
import com.github.typingtanuki.restit.model.RestResponse;
import com.github.typingtanuki.restit.model.Url;
import com.github.typingtanuki.restit.model.internal.RestResponseBuilder;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * A minimalist helper for writing REST based integration tests
 *
 * @author Clerc Mathias
 */
public class RestIt {
    private static ObjectMapper MAPPER = new ObjectMapper();

    private String serverUrl;
    private String basicUser;
    private String basicPassword;

    /**
     * The base URL to connect to the server
     *
     * @param serverUrl the utl, including <code>http://</code> and port number
     */
    public RestIt(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public RestResponse rest(RestRequest request) {
        try {
            return unrest(request);
        } catch (IOException | RuntimeException e) {
            throw new AssertionError("Error connecting to REST API for: " + request, e);
        }
    }

    public RestResponse unrest(RestRequest request) throws IOException {
        WebTarget resource = resourceFor(request.getUrl());

        Invocation.Builder builder = resource.request();
        builder.accept(MediaType.APPLICATION_JSON);

        RestResponseBuilder responseBuilder = new RestResponseBuilder(request);
        Response response;
        switch (request.getMethod()) {
            case GET:
                response = builder.get();
                break;
            case POST:
                response = builder.post(entity(request));
                break;
            case PUT:
                response = builder.post(entity(request));
                break;
            case DELETE:
                response = builder.delete();
                break;
            default:
                throw new IOException("Unknown http method " + request.getMethod());
        }
        return responseBuilder.build(response);
    }

    private Entity<String> entity(RestRequest request) throws IOException {
        if (request.getEntity() == null) {
            return Entity.json(null);
        }
        return Entity.json(MAPPER.writer().writeValueAsString(request.getEntity()));
    }

    /**
     * Define HTTP basic authentication to be used by the client (if needed)
     *
     * @param user     the name of the user
     * @param password the password of the user
     */
    public void basicAuth(String user, String password) {
        this.basicUser = user;
        this.basicPassword = password;
    }

    /**
     * Builds a Resteasy client for the specified URL
     *
     * @param url the URL to connect to
     * @return a Resteasy client
     */
    protected WebTarget resourceFor(Url url) {
        Client client = ResteasyClientBuilder.newClient();
        WebTarget resource = client.target(serverUrl + url.build());

        if (basicUser != null && basicPassword != null) {
            resource.register(new BasicAuthentication(basicUser, basicPassword));
        }

        return resource;
    }
}
