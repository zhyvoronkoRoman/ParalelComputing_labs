package com.edu.cryptoTradingApp.utils.exceptions;

import com.edu.cryptoTradingApp.utils.exceptions.types.BadRequestException;
import com.edu.cryptoTradingApp.utils.exceptions.types.WalletNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String massage, String path){
        ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(),status.value(),status.getReasonPhrase(), massage, path);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse>BadRequestException(BadRequestException ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ApiErrorResponse>handleWalletNotFoundException(WalletNotFoundException ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFound(NoResourceFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Endpoint not found: " + ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected error occurred: " + ex.getMessage(), request.getRequestURI());
    }
}
