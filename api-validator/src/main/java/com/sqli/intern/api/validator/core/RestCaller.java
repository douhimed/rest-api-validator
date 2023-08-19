package com.sqli.intern.api.validator.core;

import com.sqli.intern.api.validator.utilities.models.AuthHeaderProvider;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface RestCaller {
    void runTest(ResponseDto responseDto);
    void runTest(ResponseDto responseDto, AuthHeaderProvider authHeaderProvider);

}
