package com.github.typingtanuki.restit;

import com.github.typingtanuki.restit.mocks.RestItForTest;
import com.github.typingtanuki.restit.mocks.TestResponse;
import com.github.typingtanuki.restit.model.HttpMethod;
import com.github.typingtanuki.restit.model.RestRequest;
import com.github.typingtanuki.restit.model.RestResponse;
import com.github.typingtanuki.restit.model.Url;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Clerc Mathias
 */
public class DelegationTest {
    private final RestIt restIt = new RestItForTest("http://testServer.com");

    @Test
    public void GETisGET() {
        RestResponse response = restIt.GET(testUrl(200));
        response.assertStatus(200);
        assertThat(response.getRequest().getMethod(), is(HttpMethod.GET));
        TestResponse entity = response.getEntity(TestResponse.class);
        assertThat(entity.getMethod(), is(HttpMethod.GET));
    }

    @Test
    public void POSTisPOST() {
        String bodyString = "This is a body -- put";
        RestResponse response = restIt.POST(testUrl(201), bodyString);
        response.assertStatus(201);
        TestResponse entity = response.getEntity(TestResponse.class);
        assertThat(entity.getBody(), containsString(bodyString));
        assertThat(response.getRequest().getMethod(), is(HttpMethod.POST));
        assertThat(entity.getMethod(), is(HttpMethod.POST));
    }

    @Test
    public void PUTisPUT() {
        String bodyString = "This is a body -- put";
        RestResponse response = restIt.PUT(testUrl(201), bodyString);
        response.assertStatus(201);
        TestResponse entity = response.getEntity(TestResponse.class);
        assertThat(entity.getBody(), containsString(bodyString));
        assertThat(response.getRequest().getMethod(), is(HttpMethod.PUT));
        assertThat(entity.getMethod(), is(HttpMethod.PUT));
    }

    @Test
    public void DELETEisDELETE() {
        RestResponse response = restIt.DELETE(testUrl(200));
        response.assertStatus(200);
        assertThat(response.getRequest().getMethod(), is(HttpMethod.DELETE));
        TestResponse entity = response.getEntity(TestResponse.class);
        assertThat(entity.getMethod(), is(HttpMethod.DELETE));
    }

    private Url testUrl(int returnedStatus) {
        Url url = new Url("/test/url");
        url.addQueryParameter("status", returnedStatus);
        return url;
    }
}