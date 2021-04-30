package com.ad.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class SMExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(SMException.class)
    public ResponseEntity<Object> handleTechnicalException(
            SMException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errorCode", ex.getErrorEnum().getCode() );
        body.put("errorMessage", ex.getErrorEnum().getMsg());

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
