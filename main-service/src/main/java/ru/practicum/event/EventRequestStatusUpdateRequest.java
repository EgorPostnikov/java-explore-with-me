package ru.practicum.event;


import ru.practicum.requests.ParticipationRequestDto;

public class EventRequestStatusUpdateRequest {
    private ParticipationRequestDto confirmedRequests;
    private ParticipationRequestDto rejectedRequests;
}
