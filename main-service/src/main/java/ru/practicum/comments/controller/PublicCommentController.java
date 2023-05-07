package ru.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiError.Response;
import ru.practicum.comments.service.CommentService;
import ru.practicum.comments.dto.CommentDto;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PublicCommentController {
    private final CommentService service;

    @GetMapping("/{comId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getComment(@PathVariable Integer comId) {
        log.info("Get comment #{}", comId);
        return service.getComment(comId);
    }

    @GetMapping("/{eventId}/event")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CommentDto> getCommentsforEvent(
            @PathVariable Integer eventId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        log.info("Get comments");
        return service.getCommentsOfEvent(pageRequest,eventId);
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
