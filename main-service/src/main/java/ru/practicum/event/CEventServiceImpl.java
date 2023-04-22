package ru.practicum.event;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.requests.*;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class CEventServiceImpl implements BEventService {
    private static final Logger log = LoggerFactory.getLogger(CEventServiceImpl.class);


    EventRepository repository;
    UserRepository userRepository;
    RequestRepository requestRepository;

    private final RequestServiceImpl requestService;

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
        if (!oldEvent.getRequestModeration()||!oldEvent.getState().equals("CANCELLED")){
            throw new RuntimeException("Only pending or canceled events can be changed");
        }
        LocalDateTime newEventDate = LocalDateTime.parse(newEvent.getEventDate(), formatter);
        if (newEventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new RuntimeException("Дата и время на которые намечено событие не может быть раньше," +
                    " чем через два часа от текущего момента");
        } else if (!(newEvent.getEventDate() == null)) {
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
            oldEvent.setState(newEvent.getStateAction());
            //oldEvent.setState("CANCELED");// удалить
        }
        if (!(newEvent.getTitle() == null)) {
            oldEvent.setTitle(newEvent.getTitle());
        }

        Event createdEntity = repository.save(oldEvent);
        log.info("Event with id #{} updated", createdEntity.getId());
        return EventMapper.INSTANCE.toEventFullDto(createdEntity);
    }

    @Override
    public Collection<ParticipationRequestDto> getRequestsForEventsOfUser(Integer userId, Integer eventId) {
        Collection<ParticipationRequest> userRequestsForEvent = requestRepository.
                getParticipationRequestsByEventIsAndAndRequesterIs(userId, eventId);

        log.info("Requests of user id #{} for event id #{}  get, requests qty is {}", userId, eventId, userRequestsForEvent.size());
        if (userRequestsForEvent.isEmpty()) {
            userRequestsForEvent = Collections.emptyList();
        }
        return RequestMapper.INSTANCE.toParticipationRequestDtos(userRequestsForEvent);
    }

    @Override
    public EventRequestStatusUpdateResult changeEventsRequestStatus(Integer userId,
                                                                    Integer eventId,
                                                                    EventRequestStatusUpdateRequest requestDto) {
        List<Integer> requestIds = requestDto.getRequestIds();
        String status = requestDto.getStatus();
        EventFullDto event = getFullEventInfo(userId, eventId);
        Integer participantLimit = event.getParticipantLimit();
        Integer confirmedRequests = event.getConfirmedRequests();
        HashSet<ParticipationRequest> requestsForEvent = requestRepository.getParticipationRequestsByEventIsOrderById(eventId);

        for (Integer requestId : requestIds) {
            int i=1;
            if (i>confirmedRequests) {

                i++;
            }


        }
        return new EventRequestStatusUpdateResult(Collections.emptyList(), Collections.emptyList());
        //если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
        //нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409)
        //статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409)
        //если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить

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
        Collection<EventFullDto> events = EventMapper.INSTANCE.toEventFullDtos(entities);
        if (events.isEmpty()) {
            events = Collections.emptyList();
        }
        log.info("Events get, events qty is {}", events.size());

        return events;
    }

    @Override
    public EventFullDto updateEvent(Integer eventId, UpdateEventAdminRequest requestDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Event oldEvent = repository.findById(eventId).get();
        if (oldEvent == null) {
            throw new EntityNotFoundException("Event  id #" + eventId + " did not found");
        }
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
            oldEvent.setEventDate(LocalDateTime.parse(requestDto.getEventDate(), formatter));
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
            oldEvent.setState(requestDto.getStateAction());
            oldEvent.setState("CANCELED");// удалить
        }
        if (!(requestDto.getTitle() == null)) {
            oldEvent.setTitle(requestDto.getTitle());
        }

        Event createdEntity = repository.save(oldEvent);
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
            entities = repository.getOnlyAvailableEventsByText(text, categories, paid, start, end, "PUBLISHED", pageRequest);
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
        Event entity = repository.findById(eventId).get();
        if (entity == null) {
            throw new EntityNotFoundException("Event with id #" + eventId + " did not found");
        }
        EventFullDto event = EventMapper.INSTANCE.toEventFullDto(entity);
        log.info("Event with id {} get", eventId);
        return event;
    }
}
