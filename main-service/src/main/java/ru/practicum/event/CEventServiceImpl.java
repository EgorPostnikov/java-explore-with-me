package ru.practicum.event;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.categories.CategoryDto;
import ru.practicum.requests.*;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import javax.persistence.EntityNotFoundException;
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
        return events;
    }
    @Override
    public EventFullDto createEvent(Integer userId, NewEventDto requestDto) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("User not found"));
        Event entity = EventMapper.INSTANCE.toEvent(requestDto, user);
        Event createdEntity = repository.save(entity);
        log.info("Event with id #{} saved", createdEntity.getId());
        return EventMapper.INSTANCE.toEventFullDto(createdEntity);
    }

    @Override
    public EventFullDto getFullEventInfo(Integer userId, Integer eventId) {
        Event entity = repository.getEventsByInitiatorIdIsAndIdIs(userId, eventId);
        if (entity==null){
            throw new EntityNotFoundException("Event for user id #"+userId+" and events id #"+eventId+" did not found");
        }
        EventFullDto event = EventMapper.INSTANCE.toEventFullDto(entity);
        log.info("Event for user id #{} and events id {} get", userId, eventId);
        return event;
    }
    @Override
    public EventFullDto updateEventOfUser(Integer userId, Integer eventId, UpdateEventUserRequest newEvent) {
        Event oldEvent = repository.getEventsByInitiatorIdIsAndIdIs(userId, eventId);
        if (oldEvent==null){
            throw new EntityNotFoundException("Event for user id #"+userId+" and events id #"+eventId+" did not found");
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
        if (!(newEvent.getEventDate() == null)) {
            oldEvent.setEventDate(newEvent.getEventDate());
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
            oldEvent.setState("CANCELED");// удалить
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
        Collection<ParticipationRequest> userRequestsForEvent= requestRepository.
                getParticipationRequestsByEventIsAndAndRequesterIs(userId, eventId);

        log.info("Requests of user id #{} for event id #{}  get, requests qty is {}", userId,eventId, userRequestsForEvent.size());
        if (userRequestsForEvent.isEmpty()) {
            userRequestsForEvent = Collections.emptyList();
        }
        return RequestMapper.INSTANCE.toParticipationRequestDtos(userRequestsForEvent);
    }
    @Override
    public EventRequestStatusUpdateResult changeEventsRequestStatus(Integer userId,
                                                                    Integer eventId,
                                                                    EventRequestStatusUpdateRequest requestDto) {
        List<Integer> requestIds =requestDto.getRequestIds();
        String status = requestDto.getStatus();
        EventFullDto event= getFullEventInfo(userId,eventId);
        Integer participantLimit = event.getParticipantLimit();
        HashSet<ParticipationRequest> requestsForEvent= requestRepository.getParticipationRequestsByEventIsOrderById(eventId);

        for (Integer requestId:requestIds) {
            //requestRepository

        }
        return new EventRequestStatusUpdateResult(Collections.emptyList(),Collections.emptyList());
        //если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
        //нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409)
        //статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409)
        //если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить

    }
}
