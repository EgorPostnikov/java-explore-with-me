package ru.practicum.comments;

import org.springframework.data.domain.PageRequest;

import java.util.Collection;

public interface CommentService {
    CommentDto createComment(CommentDto requestDto);

    void deleteComment(Integer comId);

    CommentDto updateComment(Integer comId, CommentDto requestDto);

    CommentDto getComment(Integer comId);

    Collection<CommentDto> getComments(PageRequest pageRequest);
}
