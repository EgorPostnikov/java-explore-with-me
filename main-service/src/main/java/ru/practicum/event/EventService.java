package ru.practicum.event;

import org.springframework.data.domain.PageRequest;
import ru.practicum.dto.CategoryDto;

import java.util.Collection;

public interface EventService {
    NewEventDto createEvent(Integer userId, NewEventDto requestDto);

    Collection<EventShortDto> getEventsForUser(PageRequest pageRequest, Integer userId);

    EventFullDto getFullEventInfo(Integer userId, Integer eventId);

    EventFullDto updateEventOfUser(Integer userId, Integer eventId, NewEventDto requestDto);

    Collection<EventShortDto> getRequestsForEventsOfUser(Integer userId, Integer eventId);

    CategoryDto changeEventsRequest(Integer userId, Integer eventId, CategoryDto requestDto);
}
