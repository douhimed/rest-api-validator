package com.sqli.intern.api.validator.client.rest;

import com.sqli.intern.api.validator.utilities.dtos.ExceptionMessageDto;
import com.sqli.intern.api.validator.utilities.exceptions.OperationException;
import com.sqli.intern.api.validator.utilities.exceptions.ProjectException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.function.Function;

@ControllerAdvice
public class ExceptionHandlerRestController {

    private final Function<String, ResponseEntity<Object>> handleException = message -> new ResponseEntity<>(new ExceptionMessageDto(message), HttpStatus.BAD_REQUEST);

    @ExceptionHandler(value = {ProjectException.class})
    public ResponseEntity<Object> handleProjectException(ProjectException e) {
        return handleException.apply(e.getMessage());
    }

    @ExceptionHandler(value = {OperationException.class})
    public ResponseEntity<Object> handleOperationException(OperationException operationException) {
        return handleException.apply(operationException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errors = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return new ResponseEntity<>(new ExceptionMessageDto(String.join(", ", errors)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ExceptionMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }


}

