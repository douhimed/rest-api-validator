package com.sqli.intern.api.validator.utilities.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
    @JsonProperty("op")
    private String operation;
    @JsonProperty("path")
    private String path;
    @JsonProperty("value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JsonNode value;


    public static ReportDto createErrorMessage(String errorMsg) {
        ObjectNode valueNode = JsonNodeFactory.instance.objectNode();
        valueNode.put("msg", errorMsg);

        return ReportDto.builder()
                .value(valueNode)
                .build();
    }
}
