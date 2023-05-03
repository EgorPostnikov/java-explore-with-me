package ru.practicum.comments;

import org.springframework.data.domain.PageRequest;

import java.util.Collection;

public interface CommentService {
    CommentDto createComment(CommentDto requestDto);

    void deleteComment(Integer comId);

    CommentDto updateComment(Integer comId, CommentDto requestDto);

    CommentDto getComment(Integer comId);

    Collection<CommentDto> getCommentsOfEvent(PageRequest pageRequest, Integer eventId);

    Collection<CommentDto> getCommentsOfUser(PageRequest pageRequest, Integer userId);

    CommentDto updateCommentByAuthor(Integer userId, Integer comId, CommentDto requestDto);
}
