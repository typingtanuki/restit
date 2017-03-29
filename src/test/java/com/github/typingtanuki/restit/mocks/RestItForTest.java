package com.github.typingtanuki.restit.mocks;

import com.github.typingtanuki.restit.RestIt;
import com.github.typingtanuki.restit.model.Url;

import javax.ws.rs.client.WebTarget;

/**
 * @author Clerc Mathias
 */
public class RestItForTest extends RestIt {
    public RestItForTest(String serverUrl) {
        super(serverUrl);
    }

    @Override
    protected WebTarget resourceFor(Url url) {
        return new WebTargetMock(url);
    }
}