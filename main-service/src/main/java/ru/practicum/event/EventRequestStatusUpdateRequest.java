package ru.practicum.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.requests.ParticipationRequestDto;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@ToString
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private String status;
}
