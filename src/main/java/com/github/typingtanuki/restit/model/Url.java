package com.github.typingtanuki.restit.model;

import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple object for easy manipulation of URLs
 *
 * @author Clerc Mathias
 */
public class Url {
    private final String url;
    private final Map<String, Object> queryParameters;
    private final Map<String, Object> pathParameters;

    /**
     * Creates a wrapper around the url.
     * <p>
     * The URL is given in a relative form. The base of the URL will be the server url given to the client.
     * <p>
     * The URL can contain placeholders in the path like:<br/>
     * <code>/path/to/${aPlaceholder}/and/${another}</code>
     * <p>
     * Those placeholders can then be defined at runtime with:<br/>
     * <code>addPathParameter("aPlaceholder", 12);</code><br/>
     * <code>addPathParameter("another", "isAString");</code>
     * <p>
     * This URL should not contain query parameters, they are to be added with:<br/>
     * <code>addQueryParameter("param", 23);</code><br/>
     *
     * @param url the relative URL
     */
    public Url(String url) {
        this.url = url;
        queryParameters = new LinkedHashMap<>();
        pathParameters = new LinkedHashMap<>();
    }

    /**
     * Add a path parameter to resolve a placeholder
     *
     * @param key   the key of the placeholder
     * @param value the value to be put in the url
     */
    public Url withPathParameter(String key, Object value) {
        pathParameters.put(key, value);
        return this;
    }

    /**
     * Add a query parameter to the URL
     *
     * @param key   the name of the parameter
     * @param value the value for this parameter
     */
    public Url withQueryParameter(String key, Object value) {
        queryParameters.put(key, value);
        return this;
    }

    /**
     * @return the fully formed URL, relative to the server URL with all path placeholders resolved as well as all
     * query parameters added
     */
    public String build() {
        StringBuilder fullUrl = new StringBuilder(
                new StrSubstitutor(pathParameters).replace(url)
        );
        boolean hasParam = false;
        for (Map.Entry<String, Object> parameter : queryParameters.entrySet()) {
            if (!hasParam) {
                fullUrl.append("?");
                hasParam = true;
            } else {
                fullUrl.append("&");
            }
            fullUrl.append(parameter.getKey()).append("=").append(parameter.getValue());
        }
        return fullUrl.toString();
    }
}
