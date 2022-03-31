package com.app.FirstApp.domain.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final String message;



    public ApiException(String message) {
        this.message = message;


    }
}
