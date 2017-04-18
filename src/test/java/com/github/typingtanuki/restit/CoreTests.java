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
public class CoreTests {
    private final RestIt restIt = new RestItForTest("http://testServer.com");

    @Test
    public void get200() {
        RestResponse response = restIt.rest(new RestRequest(
                HttpMethod.GET,
                testUrl(200)
        ));
        response.assertStatus(200);
        response.assertWithin(30_000);
    }

    @Test
    public void delete404() {
        RestResponse response = restIt.rest(new RestRequest(
                HttpMethod.DELETE,
                testUrl(404)
        ));
        response.assertStatus(404);
        response.assertWithin(30_000);
    }

    @Test
    public void post201() {
        String bodyString = "This is a body -- post";
        RestRequest request = new RestRequest(
                HttpMethod.POST,
                testUrl(201)
        );
        request.withEntity(bodyString);

        RestResponse response = restIt.rest(request);
        response.assertStatus(201);
        response.assertWithin(30_000);
        TestResponse entity = response.getEntity(TestResponse.class);
        assertThat(entity.getBody(), containsString(bodyString));
    }

    @Test
    public void put303() {
        String bodyString = "This is a body -- put";
        RestRequest request = new RestRequest(
                HttpMethod.PUT,
                testUrl(303)
        );
        request.withEntity(bodyString);

        RestResponse response = restIt.rest(request);
        response.assertStatus(303);
        response.assertWithin(30_000);
        TestResponse entity = response.getEntity(TestResponse.class);
        assertThat(entity.getBody(), containsString(bodyString));
    }

    @Test
    public void statusAssertionWorks() {
        RestResponse response = restIt.rest(new RestRequest(
                HttpMethod.GET,
                testUrl(404)
        ));

        boolean asserted = false;
        try {
            response.assertStatus(999);
        } catch (AssertionError e) {
            asserted = true;
        }

        assertThat("Status assertion did not trigger", asserted, is(true));
    }

    @Test
    public void timeAssertionWorks() {
        RestResponse response = restIt.rest(new RestRequest(
                HttpMethod.GET,
                testUrl(404)
        ));

        boolean asserted = false;
        try {
            response.assertWithin(-10);
        } catch (AssertionError e) {
            asserted = true;
        }

        assertThat("Time assertion did not trigger", asserted, is(true));
    }

    private Url testUrl(int returnedStatus) {
        return new Url("/test/url").withQueryParameter("status", returnedStatus);
    }
}