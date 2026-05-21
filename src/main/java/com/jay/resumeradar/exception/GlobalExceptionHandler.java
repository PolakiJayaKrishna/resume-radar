package com.jay.resumeradar.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.jay.resumeradar.dto.ErrorResponse;
import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRunTimeException(ResourceNotFoundException exception, jakarta.servlet.http.HttpServletRequest request){
       ErrorResponse errorResponse = ErrorResponse.builder()
               .timestamp(LocalDateTime.now())
               .status(404)
               .error("Not Found")
               .message(exception.getMessage())
               .path(request.getRequestURI())
               .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(org.springframework.web.bind.MissingServletRequestParameterException ex, jakarta.servlet.http.HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(400) // 400 Bad Request
                .error("Bad Request")
                .message("You forgot a required parameter: " + ex.getParameterName())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, jakarta.servlet.http.HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(java.time.LocalDateTime.now())
                .status(500) // 500 Internal Server Error
                .error("Internal Server Error")
                .message("Something went wrong on our end. Please try again later.")
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(500).body(error);
    }


}
