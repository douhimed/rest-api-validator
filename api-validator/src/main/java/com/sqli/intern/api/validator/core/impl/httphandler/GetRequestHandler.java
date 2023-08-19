package com.sqli.intern.api.validator.core.impl.httphandler;

import com.sqli.intern.api.validator.core.impl.jsonhandler.JsonHandler;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

@Component
public class GetRequestHandler extends RestHandler {

    @Override
    public HttpMethod getType() {
        return HttpMethod.GET;
    }

    @Override
    public HttpEntity getBody(ResponseDto responseDto) {
        return null;
    }

    @Override
    public JsonHandler getNext() {
        return queryValidator;
    }
}
