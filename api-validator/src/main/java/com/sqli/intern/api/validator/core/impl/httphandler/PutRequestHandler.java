package com.sqli.intern.api.validator.core.impl.httphandler;

import com.sqli.intern.api.validator.core.impl.jsonhandler.JsonHandler;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class PutRequestHandler extends RestHandler {

    @Override
    public HttpMethod getType() {
        return HttpMethod.PUT;
    }

    @Override
    public HttpEntity getBody(ResponseDto responseDto) {
        return new HttpEntity<>(responseDto.getBody());
    }

    @Override
    public JsonHandler getNext() {
        return commandValidator;
    }

}
