package com.kagimbz.cloud_nine.configs;

import com.kagimbz.cloud_nine.dto.Error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleExceptions(Exception e) {
        log.error("An error occurred during processing. Error: {}", e.getLocalizedMessage(), e);
        return ErrorResponse.builder()
                .message("An error occurred during processing. Please try again later")
                .build();
    }

}
