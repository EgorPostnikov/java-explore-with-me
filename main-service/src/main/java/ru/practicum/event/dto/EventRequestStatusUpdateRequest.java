package ru.practicum.event.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private String status;
}
