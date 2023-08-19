package com.sqli.intern.api.validator.utilities.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OperationDto> operationDtos;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ResponseDto> responseDto;
    private boolean withAuth;

}
