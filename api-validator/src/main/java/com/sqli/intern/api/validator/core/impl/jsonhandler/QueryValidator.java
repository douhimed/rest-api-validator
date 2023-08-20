package com.sqli.intern.api.validator.core.impl.jsonhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.sqli.intern.api.validator.utilities.dtos.ReportDto;
import com.sqli.intern.api.validator.utilities.JsonUtils;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;
import com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum;
import com.sqli.intern.api.validator.utilities.enums.OperationTypeEnum;
import com.sqli.intern.api.validator.utilities.enums.ValidationStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class QueryValidator extends JsonHandler {

    private static final String PATH = "/op";
    private static final String MOVE = "move";

    @Override
    protected void invokeValidation(JsonNode expected, ResponseDto responseDto) {
        try {
            String actualResponse = responseDto.getActualResponse();

            if (actualResponse == null) {
                responseDto.addMessage(ReportDto.createErrorMessage(ExceptionMessageEnum.BAD_REQUEST.getMessage()));
                responseDto.setValidationStatus(ValidationStatus.BAD_REQUEST);
                return;
            }

            JsonNode currentJsonNode = objectMapper.readTree(actualResponse);
            JsonNode patch = JsonDiff.asJson(expected, currentJsonNode);
            List<ReportDto> validationMessages = createValidationMessages(patch);
            responseDto.setMessages(validationMessages);
            setValidationStatus(responseDto);
        } catch (JsonProcessingException e) {
            responseDto.addMessage(ReportDto.createErrorMessage("INVALID FORMAT"));
            responseDto.setValidationStatus(ValidationStatus.INVALID);
        }
    }

    private List<ReportDto> createValidationMessages(JsonNode patch) {
        List<ReportDto> validationMessages = new ArrayList<>();

        for (JsonNode node : patch) {
            if (JsonUtils.isNodeValueNotEqual(node, PATH, MOVE)) {
                ReportDto reportDto = ReportDto.builder()
                        .operation(node.get("op").asText())
                        .path(node.get("path").asText())
                        .value(node.get("value"))
                        .build();

                validationMessages.add(reportDto);
            }
        }

        return validationMessages;
    }


    private static void setValidationStatus(ResponseDto responseDto) {
        List<ReportDto> messages = responseDto.getMessages();
        ValidationStatus validationStatus = (messages != null && !messages.isEmpty())
                ? ValidationStatus.INVALID
                : ValidationStatus.VALID;
        responseDto.setValidationStatus(validationStatus);
    }

    private static ReportDto mapJsonToReportDto(JsonNode node) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(node, ReportDto.class);
    }

    @Override
    public void compareJson(ResponseDto responseDto) {
        invoke(responseDto, null);
    }

    @Override
    public Set<String> getSupportedRequestTypes() {
        Set<String> supportedRequestTypes = new HashSet<>();
        supportedRequestTypes.add(OperationTypeEnum.GET.name());
        return supportedRequestTypes;
    }
}
