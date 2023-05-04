package ru.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiError.Response;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.service.CommentServiceImpl;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class PrivateCommentController {
    private final CommentServiceImpl service;

    @PostMapping("/{userId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable() Integer userId,
                                    @Valid @RequestBody(required = false) CommentDto requestDto) {
        log.info("Creating event {} by user {}", requestDto, userId);
        return service.createComment(userId, requestDto);
    }
    @PatchMapping("/{userId}/comments/{comId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateCommentByAuthor(@PathVariable() Integer userId,
                                            @PathVariable(required = false) Integer comId,
                                            @RequestBody CommentDto requestDto) {
        log.info("Updating comment {} by user {}, by data {}", comId, userId, requestDto);
        return service.updateCommentByAuthor(userId, comId, requestDto);
    }
    @GetMapping("/{userId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CommentDto> getCommentsOfUser(@PathVariable() Integer userId,
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        log.info("Get all comments from {},size {}, for user {}", from, size, userId);
        return service.getCommentsOfUser(pageRequest, userId);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public Response handleException(NoSuchElementException exception) {
        return new Response(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Response handleException(EntityNotFoundException exception) {
        return new Response(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RuntimeException.class)
    public Response handleException(RuntimeException exception) {
        return new Response(exception.getMessage());
    }

}
