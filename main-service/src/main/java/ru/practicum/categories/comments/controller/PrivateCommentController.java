package ru.practicum.categories.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.comments.dto.NewCommentDto;
import ru.practicum.categories.comments.dto.UpdateCommentDto;
import ru.practicum.categories.comments.dto.CommentDto;
import ru.practicum.categories.comments.service.CommentServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PrivateCommentController {
    private final CommentServiceImpl service;

    @PostMapping("/{userId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable() Integer userId,
                                    @Valid @RequestBody() NewCommentDto requestDto) {
        log.info("Creating event {} by user {}", requestDto, userId);
        return service.createComment(userId, requestDto);
    }

    @PatchMapping("/{userId}/comments/{comId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateCommentByAuthor(@PathVariable() Integer userId,
                                            @PathVariable() Integer comId,
                                            @Valid @RequestBody UpdateCommentDto requestDto) {
        log.info("Updating comment {} by user {}, by data {}", comId, userId, requestDto);
        return service.updateCommentByAuthor(userId, comId, requestDto);
    }

    @GetMapping("/{userId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CommentDto> getCommentsOfUser(@PathVariable() Integer userId,
                                                    @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                    @Positive @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        log.info("Get all comments from {},size {}, for user {}", from, size, userId);
        return service.getCommentsOfUser(pageRequest, userId);
    }

}
