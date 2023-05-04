package ru.practicum.comments.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.comments.model.Comment;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {


    Collection<Comment> getCommentsByAuthorIdIs(Integer userId, PageRequest pageRequest);

    Collection<Comment> getCommentsByEventIdIs(Integer eventId, PageRequest pageRequest);
}
