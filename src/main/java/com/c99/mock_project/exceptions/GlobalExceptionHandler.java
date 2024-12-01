package com.c99.mock_project.exceptions;

import com.c99.mock_project.models.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ModelMapper modelMapper;

    // Logger to log exceptions and responses
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        // Map exception to ErrorResponse
        ErrorResponse errorResponse = modelMapper.map(ex, ErrorResponse.class);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError("Resource NOT FOUND");  // General error message
        errorResponse.setMessage(ex.getMessage());  // Use the exception's message
        errorResponse.setPath(request.getDescription(false));

        // Log the error details
        logger.error("Resource Not Found Exception: {}", ex.getMessage(), ex);
        logger.error("Error Response: {}", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle MethodArgumentNotValidException for bad request (e.g., validation failure)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBadRequestException(MethodArgumentNotValidException ex, WebRequest request) {
        // Collect error details in a simpler format
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        // Create an ErrorResponse object
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Bad Request");
        errorResponse.setMessage(String.join(", ", errorMessages));
        errorResponse.setPath(request.getDescription(false));

        // Log the bad request error
        logger.error("Bad Request Exception: {}", ex.getMessage(), ex);
        logger.error("Error Response: {}", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
        // Map exception to ErrorResponse
        ErrorResponse errorResponse = modelMapper.map(ex, ErrorResponse.class);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setError("Internal Server Error");  // General error message
        errorResponse.setMessage(ex.getMessage());  // Use the exception's message
        errorResponse.setPath(request.getDescription(false));

        // Log the general error
        logger.error("General Exception: {}", ex.getMessage(), ex);
        logger.error("Error Response: {}", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
