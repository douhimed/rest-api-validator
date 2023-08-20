package com.sqli.intern.api.validator.core.impl;

import com.sqli.intern.api.validator.core.impl.httphandler.RestHandler;
import com.sqli.intern.api.validator.utilities.enums.OperationTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RestStrategyHandler {

    @Autowired
    private RestHandler getRequestHandler;

    @Autowired
    private RestHandler postRequestHandler;

    @Autowired
    private RestHandler putRequestHandler;

    @Autowired
    private RestHandler deleteRequestHandler;

    public RestHandler getCaller(String type) throws InstantiationException {
        if (OperationTypeEnum.isTypeGet(type))
            return getRequestHandler;
        else if (OperationTypeEnum.isTypePost(type))
            return postRequestHandler;
        else if (OperationTypeEnum.isTypePut(type))
            return putRequestHandler;
        else if (OperationTypeEnum.isTypeDelete(type))
            return deleteRequestHandler;
        throw new InstantiationException("NOT ALLOWED OPERATION");
    }
}
