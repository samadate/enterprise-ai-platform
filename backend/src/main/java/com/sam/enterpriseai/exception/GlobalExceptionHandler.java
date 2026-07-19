package com.sam.enterpriseai.exception;

import com.sam.enterpriseai.constants.ErrorCodes;
import com.sam.enterpriseai.dto.APIErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AIProviderException.class)
    public APIErrorResponse handleAIProviderException(

            AIProviderException ex,

            HttpServletRequest request) {

        return new APIErrorResponse(

                Instant.now(),

                HttpStatus.INTERNAL_SERVER_ERROR.value(),

                ErrorCodes.AI_PROVIDER_ERROR,

                ex.getMessage(),

                request.getRequestURI()

        );
    }

}