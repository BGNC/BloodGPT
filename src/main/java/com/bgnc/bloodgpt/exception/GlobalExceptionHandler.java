package com.bgnc.bloodgpt.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException exception, WebRequest request) {
        return ResponseEntity.badRequest().body(createApiError(exception.getMessage(),request));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {

        Map<String, List<String>> errors = new HashMap<>();
        for(ObjectError objError : exception.getBindingResult().getAllErrors()) {

            if(objError instanceof FieldError fieldError) {

                String fieldName = fieldError.getField();

                if (!errors.containsKey(fieldName)) {
                    errors.put(fieldName, new ArrayList<>());
                }
                errors.get(fieldName).add(fieldError.getDefaultMessage());
            }


        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createApiError(errors,request));

    }

    private String getHostname(){
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "Unknown Host";
        }
    }

    public <E> ApiError<E> createApiError(E message,WebRequest request){

        ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());

        Exception<E> exception = new Exception<>();
        exception.setCreatedAt(new Date());
        exception.setHostName(getHostname());
        exception.setMessage(message);
        exception.setPath(request.getDescription(false).substring(4));
        apiError.setException(exception);

        return apiError;

    }
}
