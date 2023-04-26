package ru.practicum.event.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.event.dto.*;
import ru.practicum.requests.dto.ParticipationRequestDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventService {
    EventFullDto createEvent(Integer userId, NewEventDto requestDto);

    Collection<EventShortDto> getEventsForUser(PageRequest pageRequest, Integer userId);

    EventFullDto getFullEventInfo(Integer userId, Integer eventId);

    EventFullDto updateEventOfUser(Integer userId, Integer eventId, UpdateEventUserRequest requestDto);

    Collection<ParticipationRequestDto> getRequestsForEventsOfUser(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult changeEventsRequestStatus(Integer userId, Integer eventId, EventRequestStatusUpdateRequest requestDto);

    Collection<EventFullDto> getFullEventsInfo(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    EventFullDto updateEvent(Integer eventId, UpdateEventAdminRequest requestDto);

    Collection<EventShortDto> getShortEventsInfo(String text, List<Integer> categories, Boolean paid, Boolean onlyAvailable, LocalDateTime start, LocalDateTime end, String sort, PageRequest pageRequest);

    EventFullDto getFullEvent(Integer eventId);
}
