package ru.practicum.requests;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService{
    @Override
    public Collection<ParticipationRequestDto> getUserRequests(Integer userId, PageRequest pageRequest) {
        return Collections.emptyList();
    }

    @Override
    public ParticipationRequestDto createRequest(Integer userId, Integer eventId) {
        return null;
    }
    @Override
    public ParticipationRequestDto cancellRequest(Integer userId, Integer requestId) {
        return null;
    }
}
