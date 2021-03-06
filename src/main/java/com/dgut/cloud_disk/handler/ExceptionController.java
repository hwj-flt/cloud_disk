package com.dgut.cloud_disk.handler;

import com.dgut.cloud_disk.exception.ParameterException;
import com.dgut.cloud_disk.util.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = ParameterException.class)
    JSONResult handlerParameterException(Exception e) {
        return JSONResult.errorMsg(e.getMessage());
    }
}
