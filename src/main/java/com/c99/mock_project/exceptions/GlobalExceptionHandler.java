package com.c99.mock_project.exceptions;

import com.c99.mock_project.models.ApiResponse;
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
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        // Map exception to ErrorResponse
        ErrorResponse errorResponse = modelMapper.map(ex, ErrorResponse.class);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setError("Resource NOT FOUND");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getDescription(false));

        // Create ApiResponse with error details
        ApiResponse<Void> response = new ApiResponse<>(null, errorResponse);

        // Log the error details
        logger.error("Resource Not Found Exception: {}", ex.getMessage(), ex);
        logger.error("Error Response: {}", response);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(MethodArgumentNotValidException ex, WebRequest request) {
        // Collect error details in a simpler format
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        // Create ErrorResponse object
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Bad Request");
        errorResponse.setMessage(String.join(", ", errorMessages));
        errorResponse.setPath(request.getDescription(false));

        // Create ApiResponse with error details
        ApiResponse<Void> response = new ApiResponse<>(null, errorResponse);

        // Log the bad request error
        logger.error("Bad Request Exception: {}", ex.getMessage(), ex);
        logger.error("Error Response: {}", response);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex, WebRequest request) {
        // Map exception to ErrorResponse
        ErrorResponse errorResponse = modelMapper.map(ex, ErrorResponse.class);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setError("Internal Server Error");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getDescription(false));

        // Create ApiResponse with error details
        ApiResponse<Void> response = new ApiResponse<>(null, errorResponse);

        // Log the general error
        logger.error("General Exception: {}", ex.getMessage(), ex);
        logger.error("Error Response: {}", response);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
