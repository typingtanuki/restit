package com.github.typingtanuki.restit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.typingtanuki.restit.model.HttpMethod;
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

    /**
     * Do a GET on the remote URL
     * <p>
     * (wrapper around {@link RestIt#rest})
     *
     * @param url The URL to connect to
     * @return A wrapped response from the server
     */
    public RestResponse GET(Url url) {
        return rest(new RestRequest(
                HttpMethod.GET,
                url
        ));
    }

    /**
     * Do a GET on the remote URL
     * <p>
     * (wrapper around {@link RestIt#rest})
     *
     * @param url The URL to connect to
     * @return A wrapped response from the server
     */
    public RestResponse GET(String url) {
        return GET(new Url(url));
    }

    /**
     * Do a POST on the remote URL
     * <p>
     * (wrapper around {@link RestIt#rest})
     *
     * @param url    The URL to connect to
     * @param entity The entity to send with the POST
     * @return A wrapped response from the server
     */
    public RestResponse POST(Url url, Object entity) {
        return rest(new RestRequest(
                HttpMethod.POST,
                url
        ).withEntity(entity));
    }

    /**
     * Do a POST on the remote URL
     * <p>
     * (wrapper around {@link RestIt#rest})
     *
     * @param url    The URL to connect to
     * @param entity The entity to send with the POST
     * @return A wrapped response from the server
     */
    public RestResponse POST(String url, Object entity) {
        return POST(new Url(url), entity);
    }

    /**
     * Do a PUT on the remote URL
     * <p>
     * (wrapper around {@link RestIt#rest})
     *
     * @param url    The URL to connect to
     * @param entity The entity to send with the PUT
     * @return A wrapped response from the server
     */
    public RestResponse PUT(Url url, Object entity) {
        return rest(new RestRequest(
                HttpMethod.PUT,
                url
        ).withEntity(entity));
    }

    /**
     * Do a PUT on the remote URL
     * <p>
     * (wrapper around {@link RestIt#rest})
     *
     * @param url    The URL to connect to
     * @param entity The entity to send with the PUT
     * @return A wrapped response from the server
     */
    public RestResponse PUT(String url, Object entity) {
        return PUT(new Url(url), entity);
    }

    /**
     * Do a DELETE on the remote URL
     * <p>
     * (wrapper around {@link RestIt#rest})
     *
     * @param url The URL to connect to
     * @return A wrapped response from the server
     */
    public RestResponse DELETE(Url url) {
        return rest(new RestRequest(
                HttpMethod.DELETE,
                url
        ));
    }

    /**
     * Do a DELETE on the remote URL
     * <p>
     * (wrapper around {@link RestIt#rest})
     *
     * @param url The URL to connect to
     * @return A wrapped response from the server
     */
    public RestResponse DELETE(String url) {
        return DELETE(new Url(url));
    }

    /**
     * Send the request to the remote server.
     * <p>
     * All IOExceptions will be transformed into AssertionError with a clear message.
     * If the exception is wanted and expected, use {@link RestIt#unrest}.
     *
     * @param request the request to execute
     * @return the response from the server, wrapped into an helper for easier assertions
     */
    public RestResponse rest(RestRequest request) {
        try {
            return unrest(request);
        } catch (IOException | RuntimeException e) {
            throw new AssertionError("Error connecting to REST API for: " + request, e);
        }
    }

    /**
     * Send the request to the remote server.
     * <p>
     * If you do not need to handle manually the exception, use {@link RestIt#rest}.
     *
     * @param request the request to execute
     * @return the response from the server, wrapped into an helper for easier assertions
     */
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
                response = builder.put(entity(request));
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
