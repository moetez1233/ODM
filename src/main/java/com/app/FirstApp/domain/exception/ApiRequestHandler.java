package com.app.FirstApp.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiRequestHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException  e){
        // 1.  Contain paload containing exception details
       ApiException apiException= new ApiException(
                e.getMessage()


        );
        // 2. return response Entity
                return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }
}
