package ru.practicum.event.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiError.Response;
import ru.practicum.event.dto.*;
import ru.practicum.event.service.EventServiceImpl;
import ru.practicum.requests.dto.ParticipationRequestDto;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventController {
    private final EventServiceImpl service;

    @GetMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventShortDto> getEventsForUser(@PathVariable() Integer userId,
                                                      @RequestParam(defaultValue = "0") Integer from,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        log.info("Get all events from {},size {}, for user {}", from, size, userId);
        return service.getEventsForUser(pageRequest, userId);
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable() Integer userId,
                                    @Valid @RequestBody(required = false) NewEventDto requestDto) {
        log.info("Creating event {} by user {}", requestDto, userId);
        return service.createEvent(userId, requestDto);
    }


    @GetMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getFullEventInfo(@PathVariable() Integer userId,
                                         @PathVariable() Integer eventId) {
        log.info("Get event {}, for user {}", eventId, userId);
        return service.getFullEventInfo(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventOfUser(@PathVariable() Integer userId,
                                          @PathVariable(required = false) Integer eventId,
                                          @RequestBody UpdateEventUserRequest requestDto) {
        log.info("Updating event {} by user {}, by data {}", eventId, userId, requestDto);
        return service.updateEventOfUser(userId, eventId, requestDto);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ParticipationRequestDto> getRequestsForEventsOfUser(@PathVariable() Integer userId,
                                                                          @PathVariable() Integer eventId) {
        log.info("Get event {}, for user {}", eventId, userId);
        return service.getRequestsForEventsOfUser(userId, eventId);
    }


    @PatchMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult changeEventsRequestStatus(
            @PathVariable(required = false) Integer userId,
            @PathVariable(required = false) Integer eventId,
            @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        log.info("Change request of event {}  by user {}, to status {}", eventId, userId, updateRequest.getStatus());
        return service.changeEventsRequestStatus(userId, eventId, updateRequest);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public Response handleException(NoSuchElementException exception) {
        return new Response(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Response handleException(EntityNotFoundException exception) {
        return new Response(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RuntimeException.class)
    public Response handleException(RuntimeException exception) {
        return new Response(exception.getMessage());
    }

}
