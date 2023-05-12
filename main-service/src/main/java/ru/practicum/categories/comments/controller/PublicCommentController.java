package ru.practicum.categories.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.comments.dto.CommentDto;
import ru.practicum.categories.comments.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;


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
    public Collection<CommentDto> getCommentsForEvent(@PathVariable Integer eventId,
                                                      @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                      @Positive @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        log.info("Get comments");
        return service.getCommentsOfEvent(pageRequest, eventId);
    }

}
