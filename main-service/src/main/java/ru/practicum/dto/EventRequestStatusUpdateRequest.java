package ru.practicum.dto;


import ru.practicum.requests.ParticipationRequestDto;

public class EventRequestStatusUpdateRequest {
    private Integer id;
    private String description;
    private ParticipationRequestDto confirmedRequests;
    private ParticipationRequestDto rejectedRequests;
}
