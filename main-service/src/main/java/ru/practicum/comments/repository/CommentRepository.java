package ru.practicum.comments.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.model.CommentCountByEvent;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Collection<Comment> getCommentsByAuthorIdIs(Integer userId, PageRequest pageRequest);

    Collection<Comment> getCommentsByEventIdIs(Integer eventId, PageRequest pageRequest);

    @Query("select new ru.practicum.comments.model.CommentCountByEvent(com.eventId, count(com.id)) " +
            "from Comment as com " +
            "where com.eventId in ?1 " +
            "group by com.eventId " +
            "order by count(com.id) desc")
    Collection<CommentCountByEvent> countEventsByEvent(Collection<Integer> eventIds);
}
