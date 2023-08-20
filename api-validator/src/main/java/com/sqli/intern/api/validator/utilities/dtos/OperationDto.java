package com.sqli.intern.api.validator.utilities.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sqli.intern.api.validator.utilities.validation.annotations.HttpMethodValid;
import com.sqli.intern.api.validator.utilities.validation.annotations.JsonValid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {
    private Long id;
    @NotBlank(message = "Url must not be blank")
    private String url;

    @HttpMethodValid
    private String type;

    @JsonValid(message = "Body not valid")
    private String body;
    @JsonValid(message = "Expected response not valid")
    private String expectedResponse;
    private String actualResponse;
    private String expectedType;

    private String httpStatus;

    @JsonIgnore
    private Long projectId;
}


