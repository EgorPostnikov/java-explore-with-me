package ru.practicum.requests;

import org.springframework.data.domain.PageRequest;

import java.util.Collection;

public interface RequestService {
    Collection<ParticipationRequestDto> getUserRequests(Integer userId, PageRequest pageRequest);


    ParticipationRequestDto cancellRequest(Integer userId, Integer requestId);

    ParticipationRequestDto createRequest(ParticipationRequestDto request);

    ParticipationRequestDto changeRequestStatus(String status, Integer requestId);
}
