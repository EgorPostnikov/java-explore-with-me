package ru.practicum.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.User;

import java.util.Collection;

public interface EventRepository extends JpaRepository<Event, Long> {

    Collection<User> getUsersByIdIn(Collection<Integer> ids,PageRequest pageRequest);
    Collection<User> getAllBy(PageRequest pageRequest);


    Collection<Event> getEventsByCreatorIdIs(Integer userId, PageRequest pageRequest);

    Event getEventsByCreatorIdIsAndIdIs(Integer userId, Integer eventId);
}
