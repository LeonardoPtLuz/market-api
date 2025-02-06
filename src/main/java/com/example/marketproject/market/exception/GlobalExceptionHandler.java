package com.example.marketproject.market.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalExceptionHandler {


    // not found exception
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(
            EntityNotFoundException ex, 
            WebRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Não foi encontrado",
            ex.getMessage(),
            request.getDescription(false),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


     // Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
        MethodArgumentNotValidException ex,
        WebRequest request
    ) {
        // Mapeia os erros de validação
        Map<String, String> erros = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                fieldError -> fieldError.getDefaultMessage() != null
                ? fieldError.getDefaultMessage()
                :"Erro de validação"
        ));
    
    ErrorResponse error = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Erro de validação",
        "Um ou mais campos inválidos",
        request.getDescription(false),
        LocalDateTime.now(),
        erros

    );
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

      // Tratamento para erros de restrição de validação
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
        ConstraintViolationException ex,
        WebRequest request

    ) {
        Map<String, String> erros = ex.getConstraintViolations()
            .stream()
            .collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                violation -> violation.getMessage() != null
                ? violation.getMessage()
                :"Erro de validação"
            ));
    
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Erro de validação",
            "Violção de restrição de dadods",
            request.getDescription(false),
            LocalDateTime.now(),
            erros
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    // Tratamento para exceções gerais não mapeadas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralExceptions(
        Exception ex,
        WebRequest request

    ) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro interno do servidor!",
            ex.getMessage(),
            request.getDescription(false),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    
    }
}   
