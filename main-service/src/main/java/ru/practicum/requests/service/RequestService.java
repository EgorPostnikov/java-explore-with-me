package ru.practicum.requests.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.Collection;

public interface RequestService {
    Collection<ParticipationRequestDto> getUserRequests(Integer userId, PageRequest pageRequest);
    ParticipationRequestDto cancellRequest(Integer userId, Integer requestId);
    ParticipationRequestDto createRequest(ParticipationRequestDto request);
}
