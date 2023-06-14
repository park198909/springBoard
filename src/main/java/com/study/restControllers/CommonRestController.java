package com.study.restControllers;

import com.study.commons.CommonException;
import com.study.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * JSON 공통 예외 처리
 *
 */
@RestControllerAdvice("com.study.restControllers")
public class CommonRestController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData<Object>> errorHandler(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        JSONData<Object> jsonData = JSONData.builder()
                                                                .success(false)
                                                                .status(status)
                                                                .message(e.getMessage())
                                                                .build();

        return ResponseEntity.status(status).body(jsonData);
    }

}
