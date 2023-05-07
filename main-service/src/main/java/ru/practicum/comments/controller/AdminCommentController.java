package ru.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiError.Response;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.service.CommentService;

import javax.validation.ValidationException;
import java.util.NoSuchElementException;


    @RestController
    @RequestMapping(path = "/admin/comments")
    @RequiredArgsConstructor
    @Slf4j
    @Validated
    public class AdminCommentController {
        private final CommentService service;

        @DeleteMapping("/{comId}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteComment(@PathVariable Integer comId) {
            service.deleteComment(comId);
        }

        @PatchMapping("/{comId}")
        @ResponseStatus(HttpStatus.OK)
        public CommentDto updateComment(@RequestBody CommentDto requestDto,
                                          @PathVariable Integer comId) {
            if (comId == null) {
                throw new ValidationException("Field: id. Error: must not be null. Value: null");
            }
            log.info("Updating comment {}", requestDto);
            return service.updateComment(comId, requestDto);
        }


        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(ValidationException.class)
        public Response handleException(ValidationException exception) {
            return new Response(exception.getMessage());
        }

        @ResponseStatus(HttpStatus.CONFLICT)
        @ExceptionHandler(SecurityException.class)
        public Response handleException(RuntimeException exception) {
            return new Response(exception.getMessage());
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(NoSuchElementException.class)
        public Response handleException(NoSuchElementException exception) {
            return new Response(exception.getMessage());
        }

    }


