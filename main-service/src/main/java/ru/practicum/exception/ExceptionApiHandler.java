package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RuntimeException.class)
    public Response handleException(RuntimeException ex) {
        log.info("Получен статус 400 Conflict {}", ex.getMessage(), ex);
        return new Response(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Response handleException(NoSuchElementException ex) {
        log.info("Получен статус 404 Not found {}", ex.getMessage(), ex);
        return new Response(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleException(ConstraintViolationException ex) {
        log.info("Получен статус 400 Bad Request {}", ex.getMessage(), ex);
        return new Response(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(ConnectException.class)
    public Response handleException(ConnectException ex) {
        log.info("Получен статус 502 BAD_GATEWAY {}", ex.getMessage(), ex);
        return new Response(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ApiError handleException(ValidationException ex) {
        log.info("Получен статус 400 Bad Request {}", ex.getMessage(), ex);
        return new ApiError(ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().toString()
        );
    }
}
