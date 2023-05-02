package ru.practicum.requests.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventRequestStatusUpdateRequest;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.service.EventServiceImpl;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.mapper.RequestMapper;
import ru.practicum.requests.model.ParticipationRequest;
import ru.practicum.requests.repository.RequestRepository;
import ru.practicum.user.repository.UserRepository;

import java.util.*;

@Service
@AllArgsConstructor

public class RequestServiceImpl implements RequestService {
    private static final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final EventServiceImpl eventService;

    private final RequestMapper requestMapper;

    @Override
    public Collection<ParticipationRequestDto> getUserRequests(Integer userId, PageRequest pageRequest) {
        Collection<ParticipationRequest> entities = requestRepository.getParticipationRequestByRequesterIs(userId, pageRequest);
        log.info("Requests for user id #{} get, events qty is {}", userId, entities.size());
        if (entities.isEmpty()) {
            entities = Collections.emptyList();
        }
        return requestMapper.toParticipationRequestDtos(entities);
    }

    @Override
    public ParticipationRequestDto cancellRequest(Integer userId, Integer requestId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ParticipationRequest entity = requestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Request with id=" + requestId + " was not found"));
        if (entity.getRequester().equals(userId)) {
            entity.setStatus("CANCELED");
        } else {
            throw new RuntimeException("User have not roots");
        }
        ParticipationRequest createdEntity = requestRepository.save(entity);
        log.info("Request with id #{} cancelled", createdEntity.getId());
        return requestMapper.toParticipationRequestDto(createdEntity);
    }

    @Override
    public ParticipationRequestDto createRequest(ParticipationRequestDto request) {
        userRepository.findById(request.getRequester())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(request.getEvent())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        if (isRequestExist(request)) {
            throw new RuntimeException("Request already exist");
        }
        if (event.getInitiator().getId().equals(request.getRequester())) {
            throw new RuntimeException("Creator of event could not sent request for event");
        }
        if (!event.getState().equals("PUBLISHED")) {
            throw new RuntimeException("Event have not status: PUBLISHED");
        }
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new RuntimeException("Participation limit is reached");
        }
        ParticipationRequest entity = requestMapper.toParticipationRequest(request);
        ParticipationRequest createdEntity = requestRepository.save(entity);
        log.info("Request with id #{} saved", createdEntity.getId());
        if (!event.getRequestModeration()) {
            List<Integer> requestIds = new ArrayList<>();
            requestIds.add(request.getId());
            eventService.changeEventsRequestStatus(request.getRequester(),
                    request.getEvent(),
                    new EventRequestStatusUpdateRequest(requestIds, "CONFIRMED"));
        }
        return requestMapper.toParticipationRequestDto(createdEntity);
    }

    private Boolean isRequestExist(ParticipationRequestDto request) {
        Integer eventId = request.getEvent();
        Integer requesterId = request.getRequester();
        return requestRepository.existsByEventIsAndRequesterIs(eventId, requesterId);
    }


}
