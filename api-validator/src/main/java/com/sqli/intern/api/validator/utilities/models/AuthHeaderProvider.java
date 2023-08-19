package com.sqli.intern.api.validator.utilities.models;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class AuthHeaderProvider {
    private final String username;
    private final String password;

    public AuthHeaderProvider(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setAuthHeader(HttpHeaders headers) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
    }

    public void setDefaultHttpHeaders(HttpHeaders headers) {
    }

    public HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (isAuthRequired()) setAuthHeader(headers);
        else setDefaultHttpHeaders(headers);
        return headers;
    }

    private boolean isAuthRequired() {
        return StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password);
    }

}
