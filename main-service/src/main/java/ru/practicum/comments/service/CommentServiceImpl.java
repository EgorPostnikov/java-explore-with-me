package ru.practicum.comments.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.mapper.CommentMapper;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.repository.CommentRepository;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentMapper commentMapper;

    public CommentDto createComment(Integer userId, CommentDto requestDto) {
        Comment entity = commentMapper.toComment(requestDto, userId);
        commentValidation(entity);
        Comment createdEntity = repository.save(entity);
        log.info("Comment {} with id #{} saved", createdEntity.getText(), createdEntity.getId());
        return commentMapper.toCommentDto(createdEntity);
    }

    @Override
    public CommentDto updateCommentByAuthor(Integer userId, Integer comId, CommentDto newComment) {
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> new NoSuchElementException("Comment was not found"));
        if (!(Objects.equals(comment.getAuthorId(), userId))) {
            throw new ValidationException("User have not roots to update comment");
        }
        if (!newComment.getText().isEmpty()) {
            comment.setText(newComment.getText());
        }
        Comment createdEntity = repository.save(comment);
        log.info("Comment {} with id #{} updated", createdEntity.getText(), createdEntity.getId());
        return commentMapper.toCommentDto(createdEntity);
    }

    @Override
    public Collection<CommentDto> getCommentsOfUser(PageRequest pageRequest, Integer userId) {
        Collection<Comment> comments = repository.getCommentsByAuthorIdIs(userId, pageRequest);
        log.info("Comments of user {} got, qty={}", userId, comments.size());
        if (comments.isEmpty()) {
            return Collections.emptyList();
        }
        return commentMapper.toCommentDtos(comments);
    }

    @Override
    public CommentDto getComment(Integer comId) {
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> new NoSuchElementException("Comment was not found"));
        log.info("Comment with id #{} got", comId);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public Collection<CommentDto> getCommentsOfEvent(PageRequest pageRequest, Integer eventId) {
        Collection<Comment> comments = repository.getCommentsByEventIdIs(eventId, pageRequest);
        log.info("Comments of event {} got, qty={}", eventId, comments.size());
        if (comments.isEmpty()) {
            return Collections.emptyList();
        }
        return commentMapper.toCommentDtos(comments);
    }

    @Override
    public void deleteComment(Integer comId) {
        isCommentExist(comId);
        repository.deleteById(comId);
        log.info("Comment with id #{} deleted", comId);
    }

    @Override
    public CommentDto updateComment(Integer comId, CommentDto requestDto) {
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> new NoSuchElementException("Comment was not found"));
        Comment newComment = commentMapper.toComment(requestDto, requestDto.getAuthorId());
        newComment.setId(comId);
        commentValidation(newComment);
        if (!newComment.getText().isEmpty()) {
            comment.setText(newComment.getText());
        }
        Comment createdEntity = repository.save(comment);
        log.info("Comment {} with id #{} updated", createdEntity.getText(), createdEntity.getId());
        return commentMapper.toCommentDto(createdEntity);
    }

    private void isCommentExist(Integer comId) {
        if (!repository.existsById(comId)) {
            throw new NoSuchElementException("Comment with id=" + comId + " was not found");
        }
    }

    private void commentValidation(Comment comment) {
        String text = comment.getText();
        if (text == null) {
            throw new ValidationException("Field: text. Error: must not be null. Value: null");
        } else if (text.isBlank()) {
            throw new ValidationException("Field: text. Error: must not be blank. Value: null");
        }
    }
}