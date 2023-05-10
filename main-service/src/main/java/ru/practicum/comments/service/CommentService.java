package ru.practicum.comments.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.dto.UpdateCommentDto;

import java.util.Collection;

public interface CommentService {

    void deleteComment(Integer comId);

    NewCommentDto updateComment(Integer comId, UpdateCommentDto requestDto);

    NewCommentDto getComment(Integer comId);

    Collection<NewCommentDto> getCommentsOfEvent(PageRequest pageRequest, Integer eventId);

    Collection<NewCommentDto> getCommentsOfUser(PageRequest pageRequest, Integer userId);

    NewCommentDto updateCommentByAuthor(Integer userId, Integer comId, UpdateCommentDto requestDto);
}
