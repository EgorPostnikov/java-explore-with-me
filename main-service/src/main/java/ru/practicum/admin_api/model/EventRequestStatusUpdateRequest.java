package ru.practicum.admin_api.model;


public class EventRequestStatusUpdateRequest {
    private Integer id;
    private String description;
    private ParticipationRequestDto confirmedRequests;
    private ParticipationRequestDto rejectedRequests;
}
