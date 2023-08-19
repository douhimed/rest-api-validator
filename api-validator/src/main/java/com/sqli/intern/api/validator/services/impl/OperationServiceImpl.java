package com.sqli.intern.api.validator.services.impl;

import com.sqli.intern.api.validator.utilities.models.AuthHeaderProvider;
import com.sqli.intern.api.validator.core.JsonComparator;
import com.sqli.intern.api.validator.core.impl.RestStrategyHandler;
import com.sqli.intern.api.validator.entities.OperationEntity;
import com.sqli.intern.api.validator.repositories.OperationRepository;
import com.sqli.intern.api.validator.services.OperationService;
import com.sqli.intern.api.validator.services.OperationValidator;
import com.sqli.intern.api.validator.utilities.dtos.OperationDto;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;
import com.sqli.intern.api.validator.utilities.enums.OperationTypeEnum;
import com.sqli.intern.api.validator.utilities.exceptions.OperationException;
import com.sqli.intern.api.validator.utilities.mappers.OperationMapper;
import com.sqli.intern.api.validator.utilities.mappers.RequestResponseMapper;
import com.sqli.intern.api.validator.utilities.enums.ValidationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum.NOT_FOUND_OPERATION;
import static com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum.NOT_VALID_HTTP_METHOD;


@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private RestStrategyHandler restStrategyHandler;

    @Autowired
    private JsonComparator queryValidator;
    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private GetValidator getValidator;
    @Autowired
    private PostValidator postValidator;
    @Autowired
    private PutValidator putValidator;
    @Autowired
    private DeleteValidator deleteValidator;

    @Override
    public ValidationStatus compareJson(OperationDto operationDto) {
        final ResponseDto responseDto = RequestResponseMapper.map(operationDto);
        queryValidator.compareJson(responseDto);
        return responseDto.getValidationStatus();
    }

    @Override
    public ResponseDto runTest(OperationDto operationDto) throws InstantiationException {
        final ResponseDto responseDto = RequestResponseMapper.map(operationDto);
        restStrategyHandler.getCaller(operationDto.getType()).runTest(responseDto);
        return responseDto;
    }

    @Override
    public ResponseDto runTest(OperationDto operationDto, AuthHeaderProvider authHeaderProvider) throws InstantiationException {
        final ResponseDto responseDto = RequestResponseMapper.map(operationDto);
        restStrategyHandler.getCaller(operationDto.getType()).invoke(responseDto, authHeaderProvider);
        return responseDto;
    }

    @Override
    public List<OperationDto> getAllOperations() {
        return operationRepository.findAll()
                .stream()
                .map(OperationMapper::map)
                .toList();
    }

    @Override
    public OperationDto getOperationById(Long id) {
        return operationRepository.findById(id)
                .map(OperationMapper::map)
                .orElseThrow(() -> new OperationException(NOT_FOUND_OPERATION));
    }

    @Override
    public Long addOperation(OperationDto operationDto) {
        validateOperation(operationDto);
        return operationRepository.save(OperationMapper.from(operationDto)).getId();
    }

    @Override
    public Long updateOperation(Long operationId, OperationDto operationDto) {
        final OperationEntity operationEntity = getOperationEntityOrThrowsException(operationId);
        validateOperation(operationDto);
        OperationMapper.updateOperationEntity(operationDto, operationEntity);
        return operationRepository.save(operationEntity).getId();
    }

    @Override
    public Long updateExcpectedResponse(Long operationId, String newExcpectedResponse) {
        final OperationEntity operationEntity = getOperationEntityOrThrowsException(operationId);
        operationEntity.setExpectedResponse(newExcpectedResponse);
        OperationDto operationDto = OperationMapper.map(operationEntity);
        validateOperation(operationDto);
        return operationRepository.save(operationEntity).getId();
    }

    @Override
    public Long deleteOperation(Long id) {
        OperationEntity operationEntity = getOperationEntityOrThrowsException(id);
        operationRepository.delete(operationEntity);
        return id;
    }

    @Override
    public void updateActualResponseAndHttpStatus(OperationDto operationDto, AuthHeaderProvider authHeaderProvider) throws InstantiationException {
        final ResponseDto responseDto = RequestResponseMapper.map(operationDto);
        restStrategyHandler.getCaller(operationDto.getType()).runTest(responseDto, authHeaderProvider);
        operationDto.setActualResponse(responseDto.getActualResponse());
        operationDto.setHttpStatus(responseDto.getHttpStatus());
        updateOperation(operationDto.getId(), operationDto);
    }


    private void validateOperation(OperationDto operationDto) {
        getValidator(OperationTypeEnum.from(operationDto.getType())).validate(operationDto);
    }

    private OperationValidator getValidator(OperationTypeEnum method) throws OperationException {
        return switch (method) {
            case GET -> getValidator;
            case POST -> postValidator;
            case PUT -> putValidator;
            case DELETE -> deleteValidator;
            default -> throw new OperationException(NOT_VALID_HTTP_METHOD);
        };
    }

    private OperationEntity getOperationEntityOrThrowsException(Long id) {
        return this.operationRepository.findById(id)
                .orElseThrow(() -> new OperationException(NOT_FOUND_OPERATION));
    }
}

