package ru.practicum.event.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.StatsClient;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.mapper.RequestMapper;
import ru.practicum.requests.model.ParticipationRequest;
import ru.practicum.requests.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor

public class EventServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    EventRepository repository;
    UserRepository userRepository;
    RequestRepository requestRepository;
    StatsClient statsClient;

    @Override
    public Collection<EventShortDto> getEventsForUser(PageRequest pageRequest, Integer userId) {
        Collection<Event> entities = repository.getEventsByInitiatorIdIs(userId, pageRequest);
        Collection<EventShortDto> events = EventMapper.INSTANCE.toEventShortDtos(entities);
        log.info("Events for user id #{} get, events qty is {}", userId, events.size());
        if (events.isEmpty()) {
            events = Collections.emptyList();
        }
        return events;
    }

    @Override
    public EventFullDto createEvent(Integer userId, NewEventDto requestDto) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("User not found"));
        Event entity = EventMapper.INSTANCE.toEvent(requestDto, user);
        if (entity.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Дата и время на которые намечено событие не может быть раньше," +
                    " чем через два часа от текущего момента");
        }
        Event createdEntity = repository.save(entity);
        log.info("Event with id #{} saved", createdEntity.getId());
        return EventMapper.INSTANCE.toEventFullDto(createdEntity);
    }

    @Override
    public EventFullDto getFullEventInfo(Integer userId, Integer eventId) {
        Event entity = repository.getEventsByInitiatorIdIsAndIdIs(userId, eventId);
        if (entity == null) {
            throw new EntityNotFoundException("Event for user id #" + userId + " and events id #" + eventId + " did not found");
        }
        entity.setViews(getViews(entity));
        EventFullDto event = EventMapper.INSTANCE.toEventFullDto(entity);
        log.info("Event for user id #{} and events id {} get", userId, eventId);
        return event;
    }

    @Override
    public EventFullDto updateEventOfUser(Integer userId, Integer eventId, UpdateEventUserRequest newEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Event oldEvent = repository.getEventsByInitiatorIdIsAndIdIs(userId, eventId);
        if (oldEvent == null) {
            throw new EntityNotFoundException("Event for user id #" + userId + " and events id #" + eventId + " did not found");
        }
        if (oldEvent.getState().equals("PUBLISHED")) {
            throw new RuntimeException("Only pending or canceled events can be changed");
        }

        if (!(newEvent.getEventDate() == null)) {
            LocalDateTime newEventDate = LocalDateTime.parse(newEvent.getEventDate(), formatter);
            if (newEventDate.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new RuntimeException("Дата и время на которые намечено событие не может быть раньше," +
                        " чем через два часа от текущего момента");
            }
            oldEvent.setEventDate(newEventDate);
        }
        if (!(newEvent.getAnnotation() == null)) {
            oldEvent.setAnnotation(newEvent.getAnnotation());
        }
        if (!(newEvent.getCategory() == null)) {
            oldEvent.getCategory().setId(newEvent.getCategory());
        }
        if (!(newEvent.getDescription() == null)) {
            oldEvent.setDescription(newEvent.getDescription());
        }
        if (!(newEvent.getLocation() == null)) {
            oldEvent.setLocation(newEvent.getLocation());
        }
        if (!(newEvent.getPaid() == null)) {
            oldEvent.setPaid(newEvent.getPaid());
        }
        if (!(newEvent.getParticipantLimit() == null)) {
            oldEvent.setParticipantLimit(newEvent.getParticipantLimit());
        }
        if (!(newEvent.getRequestModeration() == null)) {
            oldEvent.setRequestModeration(newEvent.getRequestModeration());
        }
        if (!(newEvent.getStateAction() == null)) {
            if (newEvent.getStateAction().equals("SEND_TO_REVIEW")) {
                oldEvent.setState("PENDING");
            } else if (newEvent.getStateAction().equals("CANCEL_REVIEW")) {
                oldEvent.setState("CANCELED");
            }
        }
        if (!(newEvent.getTitle() == null)) {
            oldEvent.setTitle(newEvent.getTitle());
        }

        Event createdEntity = repository.save(oldEvent);
        log.info("Event with id #{} updated", createdEntity.getId());
        createdEntity.setViews(getViews(createdEntity));
        return EventMapper.INSTANCE.toEventFullDto(createdEntity);
    }

    @Override
    public Collection<ParticipationRequestDto> getRequestsForEventsOfUser(Integer userId, Integer eventId) {
        userId++;
        Collection<ParticipationRequest> userRequestsForEvent = requestRepository.
                getParticipationRequestsByRequesterIsAndEventIs(userId, eventId);

        log.info("Requests of user id #{} for event id #{}  get", userId, eventId);
        if (userRequestsForEvent.isEmpty()) {
            userRequestsForEvent = Collections.emptyList();
        }
        return RequestMapper.INSTANCE.toParticipationRequestDtos(userRequestsForEvent);
    }

    @Override
    public EventRequestStatusUpdateResult changeEventsRequestStatus(Integer userId,
                                                                    Integer eventId,
                                                                    EventRequestStatusUpdateRequest requestDto) {
        Event event = repository.findById(eventId).
                orElseThrow(() -> new NoSuchElementException("Event was not found"));
        Integer participantLimit = event.getParticipantLimit();
        Integer confirmedRequests = event.getConfirmedRequests();
        List<Integer> requestIds = requestDto.getRequestIds();
        String status = requestDto.getStatus();

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult(
                new ArrayList<>(), new ArrayList<>());

        if (status.equals("REJECTED")) {
            for (Integer requestId : requestIds) {
                ParticipationRequestDto request = changeRequestStatus("REJECTED", requestId);
                result.getRejectedRequests().add(request);
            }
            return result;
        }

        else if ((participantLimit == 0) || (!event.getRequestModeration())) {
            Integer finalConfirmedRequest = confirmedRequests;
            for (Integer requestId : requestIds) {
                ParticipationRequestDto request = changeRequestStatus("CONFIRMED", requestId);
                finalConfirmedRequest++;
                result.getConfirmedRequests().add(request);
            }
            event.setConfirmedRequests(finalConfirmedRequest);
            repository.save(event);
            return result;
        }
        else if (participantLimit.equals(confirmedRequests)) {
            throw new RuntimeException("The participant limit has been reached");
        } else {
            for (Integer requestId : requestIds) {
                Integer requestQty = confirmedRequests;
                if (participantLimit >= requestQty) {
                    requestQty++;
                    ParticipationRequestDto request = changeRequestStatus("CONFIRMED", requestId);
                    result.getConfirmedRequests().add(request);
                    event.setConfirmedRequests(requestQty);
                } else {
                    ParticipationRequestDto request = changeRequestStatus("REJECTED", requestId);
                    result.getRejectedRequests().add(request);
                }
            }
            repository.save(event);
            return result;
        }
    }

    @Override
    public Collection<EventFullDto> getFullEventsInfo(List<Integer> users,
                                                      List<String> states,
                                                      List<Integer> categories,
                                                      LocalDateTime start,
                                                      LocalDateTime end,
                                                      PageRequest pageRequest) {
        Collection<Event> entities = repository.
                getEventsByInitiator_IdIsInAndStateIsInAndCategory_IdIsInAndEventDateAfterAndEventDateBefore(
                        users,
                        states,
                        categories,
                        start,
                        end,
                        pageRequest);
        for (Event event : entities) {
            event.setViews(getViews(event));
        }
        Collection<EventFullDto> events = EventMapper.INSTANCE.toEventFullDtos(entities);
        if (events.isEmpty()) {
            events = Collections.emptyList();
        }
        log.info("Events get, events qty is {}", events.size());

        return events;
    }

    @Override
    public EventFullDto updateEvent(Integer eventId, UpdateEventAdminRequest requestDto) {

        Event oldEvent = repository.findById(eventId).
                orElseThrow(() -> new NoSuchElementException("Event was not found"));
        if (!(requestDto.getAnnotation() == null)) {
            oldEvent.setAnnotation(requestDto.getAnnotation());
        }
        if (!(requestDto.getCategory() == null)) {
            oldEvent.getCategory().setId(requestDto.getCategory());
        }
        if (!(requestDto.getDescription() == null)) {
            oldEvent.setDescription(requestDto.getDescription());
        }
        if (!(requestDto.getEventDate() == null)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime newEventDate = LocalDateTime.parse(requestDto.getEventDate(), formatter);
            if (newEventDate.isBefore(LocalDateTime.now().plusHours(1))) {
                throw new RuntimeException("Event time have to be not late than 1 hour of now");
            }
            oldEvent.setEventDate(newEventDate);
        }
        if (!(requestDto.getLocation() == null)) {
            oldEvent.setLocation(requestDto.getLocation());
        }
        if (!(requestDto.getPaid() == null)) {
            oldEvent.setPaid(requestDto.getPaid());
        }
        if (!(requestDto.getParticipantLimit() == null)) {
            oldEvent.setParticipantLimit(requestDto.getParticipantLimit());
        }
        if (!(requestDto.getRequestModeration() == null)) {
            oldEvent.setRequestModeration(requestDto.getRequestModeration());
        }
        if (!(requestDto.getStateAction() == null)) {
            String newState = requestDto.getStateAction();
            String oldState = oldEvent.getState();
            if (newState.equals("PUBLISH_EVENT")) {
                if (!(oldState.equals("PENDING"))) {
                    throw new RuntimeException("Cannot publish the event because it's not the right state: PUBLISHED");
                }
                oldEvent.setState("PUBLISHED");
            } else if (newState.equals("REJECT_EVENT")) {
                if (oldState.equals("PUBLISHED")) {
                    throw new RuntimeException("Cannot cancel the event because it's not in the right state: PENDING");
                }
                oldEvent.setState("CANCELLED");
            }
        }
        if (!(requestDto.getTitle() == null)) {
            oldEvent.setTitle(requestDto.getTitle());
        }

        Event createdEntity = repository.save(oldEvent);
        createdEntity.setViews(getViews(createdEntity));
        log.info("Event with id #{} updated", createdEntity.getId());
        return EventMapper.INSTANCE.toEventFullDto(createdEntity);
    }

    @Override
    public Collection<EventShortDto> getShortEventsInfo(String text,
                                                        List<Integer> categories,
                                                        Boolean paid,
                                                        Boolean onlyAvailable,
                                                        LocalDateTime start,
                                                        LocalDateTime end,
                                                        String sort,
                                                        PageRequest pageRequest) {
        Collection<Event> entities;
        text = text.toLowerCase();
        if (onlyAvailable) {
            entities = repository.getOnlyAvailableEventsByText(text,
                    categories, paid, start, end, "PUBLISHED", pageRequest);
        } else {
            entities = repository.getAllEventsByText(text, categories, paid, start, end, "PUBLISHED", pageRequest);
        }
        Collection<EventShortDto> events = EventMapper.INSTANCE.toEventShortDtos(entities);
        if (events.isEmpty()) {
            events = Collections.emptyList();
        }
        log.info("Events get, events qty is {}", events.size());
        return events;
    }

    @Override
    public EventFullDto getFullEvent(Integer eventId) {
        Event entity = repository.findById(eventId).
                orElseThrow(() -> new NoSuchElementException("Event was not found"));
        entity.setViews(getViews(entity));
        EventFullDto event = EventMapper.INSTANCE.toEventFullDto(entity);
        log.info("Event with id {} get", eventId);
        return event;
    }

    public List<EventShortDto> getShortEvents(List<Integer> ids) {
        return EventMapper.INSTANCE.toEventShortDtos(repository.getEventsByIdIn(ids));
    }

    public ParticipationRequestDto changeRequestStatus(String status, Integer requestId) {
        ParticipationRequest entity = requestRepository.findById(requestId).
                orElseThrow(() -> new NoSuchElementException("Request with id=" + requestId + " was not found"));
        if (entity.getStatus().equals("PENDING")) {
            entity.setStatus(status);
        } else {
            throw new RuntimeException("Request must have status PENDING");
        }
        ParticipationRequest createdEntity = requestRepository.save(entity);

        log.info("Status of request with id #{} was changet to {}", createdEntity.getId(), status);
        return RequestMapper.INSTANCE.toParticipationRequestDto(createdEntity);
    }

    public Integer getViews(Event event) {
        List<String> uris = new ArrayList<>();
        uris.add("events/" + event.getId());
        /*Collection<StatsDto> stats = statsClient.loadStats(new StatsRequestDto(LocalDateTime.now().minusYears(1),
                LocalDateTime.now().plusYears(1),
                uris,
                false));*/
        return 1;
    }
}
