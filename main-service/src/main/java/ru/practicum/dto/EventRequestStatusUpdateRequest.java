package ru.practicum.dto;


public class EventRequestStatusUpdateRequest {
    private Integer id;
    private String description;
    private ParticipationRequestDto confirmedRequests;
    private ParticipationRequestDto rejectedRequests;
}
