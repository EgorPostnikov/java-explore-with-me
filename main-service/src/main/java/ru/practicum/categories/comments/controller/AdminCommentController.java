package ru.practicum.categories.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.comments.dto.CommentDto;
import ru.practicum.categories.comments.dto.UpdateCommentDto;
import ru.practicum.categories.comments.service.CommentService;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
@Slf4j
public class AdminCommentController {
    private final CommentService service;

    @DeleteMapping("/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Integer comId) {
        service.deleteComment(comId);
    }

    @PatchMapping("/{comId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateComment(@Valid @RequestBody UpdateCommentDto requestDto,
                                    @PathVariable Integer comId) {
        log.info("Updating comment {}", requestDto);
        return service.updateComment(comId, requestDto);
    }

}


