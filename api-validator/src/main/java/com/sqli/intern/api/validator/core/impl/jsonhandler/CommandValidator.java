package com.sqli.intern.api.validator.core.impl.jsonhandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.sqli.intern.api.validator.utilities.ValidatorUtility;
import com.sqli.intern.api.validator.utilities.dtos.ReportDto;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;
import com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum;
import com.sqli.intern.api.validator.utilities.enums.OperationTypeEnum;
import com.sqli.intern.api.validator.utilities.enums.ValidationStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.function.Predicate;

@Component("commandValidator")
public class CommandValidator extends JsonHandler {

    private static final Map<String, Predicate<String>> PREDICATES;

    static {
        PREDICATES = new HashMap<>();
        PREDICATES.put("number", ValidatorUtility::isNumber);
        PREDICATES.put("string", ValidatorUtility::isString);
        PREDICATES.put("boolean", ValidatorUtility::isBoolean);
        PREDICATES.put("json", ValidatorUtility::isJson);
        PREDICATES.put("void", ValidatorUtility::isVoid);
    }

    @Override
    protected void invokeValidation(JsonNode patch, ResponseDto responseDto) {
        try {
            if (responseDto.getActualResponse() == null || responseDto.getActualResponse().equals("")) {
                responseDto.addMessage(ReportDto.createErrorMessage(ExceptionMessageEnum.BAD_REQUEST.getMessage()));
                responseDto.setValidationStatus(ValidationStatus.BAD_REQUEST);
                return;
            }
            String expectedType = responseDto.getExpectedType();
            String actualResponse = responseDto.getActualResponse();
            if (responseDto.getValidationStatus() == null) {
                boolean typesMatch = checkTypesMatch(expectedType, actualResponse);
                ValidationStatus status = getValidationStatus(typesMatch);
                responseDto.setValidationStatus(status);
                responseDto.setMessages(getValidationMessages(status));
            }
        } catch (HttpClientErrorException e) {
            List<ReportDto> reportDtos = new ArrayList<>();
            reportDtos.add(ReportDto.createErrorMessage("BAD REQUEST"));
            responseDto.setMessages(reportDtos);
        }
    }

    private boolean checkTypesMatch(String expectedType, String actualResponse) {
        return PREDICATES.containsKey(expectedType)
                ? PREDICATES.get(expectedType).test(actualResponse)
                : expectedType.equals(actualResponse);
    }

    private ValidationStatus getValidationStatus(boolean typesMatch) {
        return typesMatch ? ValidationStatus.VALID : ValidationStatus.INVALID;
    }

    private List<ReportDto> getValidationMessages(ValidationStatus status) {
        if (status.isInvalid()) {
            return Collections.singletonList(ReportDto.createErrorMessage("INVALID TYPE"));
        }
        return new ArrayList<>();
    }

    @Override
    public void compareJson(ResponseDto responseDto) {
        invoke(responseDto, null);
    }

    @Override
    public Set<String> getSupportedRequestTypes() {
        Set<String> supportedRequestTypes = new HashSet<>();
        supportedRequestTypes.add(OperationTypeEnum.POST.name());
        supportedRequestTypes.add(OperationTypeEnum.DELETE.name());
        supportedRequestTypes.add(OperationTypeEnum.PUT.name());
        return supportedRequestTypes;
    }

}
