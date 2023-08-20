package com.sqli.intern.api.validator.utilities.dtos;

import com.sqli.intern.api.validator.utilities.enums.ValidationStatus;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private Long id;
    private String url;
    private String type;
    private String body;
    private String expectedResponse;
    private String httpStatus;
    private String actualResponse;
    private String expectedType;

    private List<ReportDto> messages;
    private ValidationStatus validationStatus;

    public void addMessage(ReportDto reportDto) {
        initMessagesIfNull();
        this.messages.add(reportDto);
    }

    private void initMessagesIfNull() {
        if (messages == null) {
            messages = new ArrayList<>();
        }
    }

    public String getExpectedType() {
        return StringUtils.isBlank(expectedType)
                ? "json"
                : expectedType.trim().toLowerCase();
    }
}
