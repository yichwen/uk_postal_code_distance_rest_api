package io.yichwen.exception;

import io.yichwen.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostalCodeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handlePostalCodeNotFoundException(PostalCodeNotFoundException exception) {
        return Error.builder().message(exception.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Error handleException(Exception exception) {
        return Error.builder().message(exception.getMessage()).build();
    }

}
