package com.edig.imbackend.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> validationErrors = new HashMap<>();
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        errors.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handlerGlobalException(Exception exception, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = getErrorResponseDTO(request,HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handlerProductAlreadyExistsException(ProductAlreadyExistsException exception, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = getErrorResponseDTO(request,HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlerProductNotFounException(ProductNotFoundException exception, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = getErrorResponseDTO(request,HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CategoryNotExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handlerCategoryNotExistsException(CategoryNotExistsException exception, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = getErrorResponseDTO(request,HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    private static ErrorResponseDTO getErrorResponseDTO(WebRequest request,HttpStatus statusRequest ,String exception) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                request.getDescription(false),
                statusRequest,
                exception,
                LocalDateTime.now());
        return errorResponseDTO;
    }
}
