package ru.practicum.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.user.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Collection<Event> getEventsByInitiatorIdIs(Integer userId, PageRequest pageRequest);

    Event getEventsByInitiatorIdIsAndIdIs(Integer userId, Integer eventId);

    Boolean existsEventsByCategoryIdIs(Integer categoryId);
    Collection<Event> getEventsByInitiator_IdIsInAndStateIsInAndCategory_IdIsInAndEventDateAfterAndEventDateBefore(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    @Query(" select e from Event e " +
            "WHERE ((e.participantLimit - e.confirmedRequests >0) OR (e.participantLimit=0)) " +
            "AND e.paid=?3 " +
            "AND e.eventDate BETWEEN ?4 AND ?5 " +
            "AND e.category.id IN ?2 " +
            "AND e.state = ?6 " +
            "AND (upper(e.description) like upper(concat('%', ?1, '%')) " +
            " or upper(e.annotation) like upper(concat('%', ?1, '%'))) ")
    Collection<Event> getOnlyAvailableEventsByText(String text, List<Integer> categories, Boolean paid, LocalDateTime start, LocalDateTime end, String state, PageRequest pageRequest);
    @Query(" SELECT e FROM Event e " +
            "WHERE e.paid=?3 " +
            "AND e.eventDate BETWEEN ?4 AND ?5 " +
            "AND e.category.id IN ?2 " +
            "AND e.state = ?6 " +
            "AND (upper(e.description) like upper(concat('%', ?1, '%')) " +
            " or upper(e.annotation) like upper(concat('%', ?1, '%'))) ")

    Collection<Event> getAllEventsByText( String text, List<Integer> categories,Boolean paid, LocalDateTime start, LocalDateTime end, String state, PageRequest pageRequest);

    List<Event> getEventsByIdIn(List<Integer> ids);
}
