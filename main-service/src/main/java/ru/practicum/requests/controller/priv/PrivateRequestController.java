package ru.practicum.requests.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.service.RequestServiceImpl;

import java.time.LocalDateTime;
import java.util.Collection;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PrivateRequestController {

    private final RequestServiceImpl service;

    @GetMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ParticipationRequestDto> getRequest(@PathVariable Integer userId,
                                                          @RequestParam(defaultValue = "0") Integer from,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        log.info("Get Request from user {}", userId);
        return service.getUserRequests(userId, pageRequest);
    }

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable Integer userId,
                                                 @RequestParam Integer eventId) {
        log.info("Creating Request from user {}", userId);
        ParticipationRequestDto request = new ParticipationRequestDto(
                LocalDateTime.now().toString(),
                eventId,
                null,
                userId,
                "PENDING");
        return service.createRequest(request);
    }


    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancellRequest(@PathVariable Integer userId,
                                                  @PathVariable Integer requestId) {
        log.info("Cancelling Request # {} from user {}", requestId, userId);

        return service.cancellRequest(userId, requestId);
    }


}
