package ru.practicum.comments.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.dto.UpdateCommentDto;
import ru.practicum.comments.mapper.CommentMapper;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
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
    private final EventRepository eventRepository;

    public NewCommentDto createComment(Integer userId, NewCommentDto requestDto) {
        Comment entity = commentMapper.toComment(requestDto, userId);
        Event event = eventRepository.findById(entity.getEventId())
                .orElseThrow(() -> new NoSuchElementException("Event was not found"));
        if (!event.getState().equals("PUBLISHED")) {
            throw new RuntimeException("Cannot comment the event because it's not the right state: PUBLISHED");
        }
        entity.setUpdated(false);
        Comment createdEntity = repository.save(entity);
        log.info("Comment {} with id #{} saved", createdEntity.getText(), createdEntity.getId());
        return commentMapper.toCommentDto(createdEntity);
    }

    @Override
    public NewCommentDto updateCommentByAuthor(Integer userId, Integer comId, UpdateCommentDto newComment) {
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> new NoSuchElementException("Comment was not found"));
        if (!(Objects.equals(comment.getAuthorId(), userId))) {
            throw new ValidationException("User have not roots to update comment");
        }
        if (comment.getCreated().plusHours(1).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Отредактировать комментарий можно только в течении часа после его публикации");
        }
        comment.setText(newComment.getText());
        comment.setUpdated(true);
        Comment createdEntity = repository.save(comment);
        log.info("Comment {} with id #{} updated by user{}", createdEntity.getText(), createdEntity.getId(), userId);
        return commentMapper.toCommentDto(createdEntity);
    }

    @Override
    public Collection<NewCommentDto> getCommentsOfUser(PageRequest pageRequest, Integer userId) {
        Collection<Comment> comments = repository.getCommentsByAuthorIdIs(userId, pageRequest);
        log.info("Comments of user {} got, qty={}", userId, comments.size());
        if (comments.isEmpty()) {
            return Collections.emptyList();
        }
        return commentMapper.toCommentDtos(comments);
    }

    @Override
    public NewCommentDto getComment(Integer comId) {
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> new NoSuchElementException("Comment was not found"));
        log.info("Comment with id #{} got", comId);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public Collection<NewCommentDto> getCommentsOfEvent(PageRequest pageRequest, Integer eventId) {
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
    public NewCommentDto updateComment(Integer comId, UpdateCommentDto requestDto) {
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> new NoSuchElementException("Comment was not found"));
        if (comment.getCreated().plusHours(1).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Отредактировать комментарий можно только в течении часа после его публикации");
        }
        Comment newComment = commentMapper.toComment(requestDto);
        comment.setText(newComment.getText());
        comment.setUpdated(true);
        Comment createdEntity = repository.save(comment);
        log.info("Comment {} with id #{} updated", createdEntity.getText(), createdEntity.getId());
        return commentMapper.toCommentDto(createdEntity);
    }

    private void isCommentExist(Integer comId) {
        if (!repository.existsById(comId)) {
            throw new NoSuchElementException("Comment with id=" + comId + " was not found");
        }
    }


}