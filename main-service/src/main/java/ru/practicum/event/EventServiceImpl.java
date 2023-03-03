package ru.practicum.event;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService{
    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    EventRepository repository;
    @Override
    public NewEventDto createEvent(Integer userId, NewEventDto requestDto) {
        Event entity = EventMapper.INSTANCE.toEvent(requestDto);
        entity.setCreatorId(userId);
        Event createdEntity = repository.save(entity);
        log.info("Event with id #{} saved", createdEntity.getId());
        return EventMapper.INSTANCE.toNewEventDto(createdEntity);
    }
    @Override
    public Collection<EventShortDto> getEventsForUser(PageRequest pageRequest, Integer userId) {
        Collection<Event> entities = repository.getEventsByCreatorIdIs(userId, pageRequest);
        Collection<EventShortDto> events = EventMapper.INSTANCE.toEventShortDtos(entities);
        log.info("Events for user id #{} get, events qty is {}", userId, events.size());
        return events;
    }

    @Override
    public EventFullDto getFullEventInfo(Integer userId, Integer eventId) {
        Event entity = repository.getEventsByCreatorIdIsAndIdIs(userId, eventId);
        if (entity==null){
            throw new EntityNotFoundException("Event for user id #"+userId+" and events id #"+eventId+" did not found");
        }
        EventFullDto event = EventMapper.INSTANCE.toEventFullDto(entity);
        log.info("Event for user id #{} and events id {} get", userId, eventId);
        return event;
    }

    @Override
    public EventFullDto updateEventOfUser(Integer userId, Integer eventId, EventShortDto requestDto) {

      return null;
    }
}