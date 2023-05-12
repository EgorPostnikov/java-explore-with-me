package ru.practicum.comments.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.dto.UpdateCommentDto;

import java.util.Collection;

public interface CommentService {

    void deleteComment(Integer comId);

    CommentDto updateComment(Integer comId, UpdateCommentDto requestDto);

    CommentDto getComment(Integer comId);

    Collection<CommentDto> getCommentsOfEvent(PageRequest pageRequest, Integer eventId);

    Collection<CommentDto> getCommentsOfUser(PageRequest pageRequest, Integer userId);

    CommentDto createComment(Integer userId, NewCommentDto requestDto);

    CommentDto updateCommentByAuthor(Integer userId, Integer comId, UpdateCommentDto requestDto);
}
