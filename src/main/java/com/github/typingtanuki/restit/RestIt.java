package com.github.typingtanuki.restit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.typingtanuki.restit.model.RestResponse;
import com.github.typingtanuki.restit.model.Url;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
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
     * Do a GET on the specified relative URL, any failure will raise an assertion error
     *
     * @param url the URL to connect to (relative to the server URL)
     * @return A response object wrapping the response
     * @throws AssertionError for any IOException occurring during the connection
     */
    public RestResponse GET(Url url) {
        try {
            return GETWithFailure(url);
        } catch (IOException e) {
            throw new AssertionError("Error connecting to REST API for url " + url.build(), e);
        }
    }

    /**
     * Do a GET on the specified relative URL, and let any exception pass through
     * <p>
     * Recommended to be used only when connection problems are expected and under test
     *
     * @param url the URL to connect to (relative to the server URL)
     * @return A response object wrapping the response
     * @throws AssertionError for any IOException occurring during the connection
     */
    public RestResponse GETWithFailure(Url url) throws IOException {
        WebTarget resource = resourceFor(url);

        Invocation.Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        return new RestResponse(request.get(), url);
    }

    /**
     * Do a POST on the specified relative URL, any failure will raise an assertion error
     *
     * @param url  the URL to connect to (relative to the server URL)
     * @param body the body to send together with the request
     * @return A response object wrapping the response
     * @throws AssertionError for any IOException occurring during the connection
     */
    public RestResponse POST(Url url, Object body) {
        try {
            return POSTWithFailure(url, body);
        } catch (IOException e) {
            throw new AssertionError("Error connecting to REST API for url " + url.build(), e);
        }
    }

    /**
     * Do a POST on the specified relative URL, and let any exception pass through
     * <p>
     * Recommended to be used only when connection problems are expected and under test
     *
     * @param url  the URL to connect to (relative to the server URL)
     * @param body the body to send together with the request
     * @return A response object wrapping the response
     * @throws AssertionError for any IOException occurring during the connection
     */
    public RestResponse POSTWithFailure(Url url, Object body) throws IOException {
        WebTarget resource = resourceFor(url);

        Invocation.Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        Entity<Object> entity = Entity.json(MAPPER.writer().writeValueAsString(body));

        return new RestResponse(request.post(entity), url);
    }

    /**
     * Do a DELETE on the specified relative URL, any failure will raise an assertion error
     *
     * @param url the URL to connect to (relative to the server URL)
     * @return A response object wrapping the response
     * @throws AssertionError for any IOException occurring during the connection
     */
    public RestResponse DELETE(Url url) {
        try {
            return DELETEWithFailure(url);
        } catch (IOException e) {
            throw new AssertionError("Error connecting to REST API for url " + url.build(), e);
        }
    }

    /**
     * Do a DELETE on the specified relative URL, and let any exception pass through
     * <p>
     * Recommended to be used only when connection problems are expected and under test
     *
     * @param url the URL to connect to (relative to the server URL)
     * @return A response object wrapping the response
     * @throws AssertionError for any IOException occurring during the connection
     */
    public RestResponse DELETEWithFailure(Url url) throws IOException {
        WebTarget resource = resourceFor(url);

        Invocation.Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        return new RestResponse(request.delete(), url);
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
    private WebTarget resourceFor(Url url) {
        Client client = ResteasyClientBuilder.newClient();
        WebTarget resource = client.target(serverUrl + url.build());

        if (basicUser != null && basicPassword != null) {
            resource.register(new BasicAuthentication(basicUser, basicPassword));
        }

        return resource;
    }
}
