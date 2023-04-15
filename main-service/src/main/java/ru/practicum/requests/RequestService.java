package ru.practicum.requests;

import org.springframework.data.domain.PageRequest;

import java.util.Collection;

public interface RequestService {
    Collection<ParticipationRequestDto> getUserRequests(Integer userId, PageRequest pageRequest);

    ParticipationRequestDto createRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancellRequest(Integer userId, Integer requestId);
}
