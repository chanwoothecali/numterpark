package com.numble.numterpark.global.handler;

import com.numble.numterpark.user.exception.DuplicatedEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedEmailException.class)
    public final ResponseStatusException handleDuplicatedEmailException(
        DuplicatedEmailException exception) {
        log.debug("중복된 이메일입니다.", exception);

        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
