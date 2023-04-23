package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiError.Response;
import ru.practicum.requests.ParticipationRequestDto;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class APrivateEventController {
    private final CEventServiceImpl service;

    @GetMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    //Получение событий, добавленных текущим пользователем
    public Collection<EventShortDto> getEventsForUser(@PathVariable() Integer userId,
                                                      @RequestParam(defaultValue = "0") Integer from,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        log.info("Get all events from {},size {}, for user {}", from, size, userId);
        return service.getEventsForUser(pageRequest, userId);
        //+В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    //Добавление нового события
    public EventFullDto createEvent(@PathVariable() Integer userId,
                                    @Valid @RequestBody(required = false) NewEventDto requestDto) {
        log.info("Creating event {} by user {}", requestDto, userId);

        return service.createEvent(userId, requestDto);
        //+Обратите внимание: дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
    }


    @GetMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    //Получение полной информации о событии добавленном текущим пользователем
    public EventFullDto getFullEventInfo(@PathVariable() Integer userId,
                                         @PathVariable() Integer eventId) {
        log.info("Get event {}, for user {}", eventId, userId);
        return service.getFullEventInfo(userId, eventId);
        //+В случае, если события с заданным id не найдено, возвращает статус код 404
    }

    @PatchMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    //Изменение события добавленного текущим пользователем
    public EventFullDto updateEventOfUser(@PathVariable() Integer userId,
                                          @PathVariable(required = false) Integer eventId,
                                          @RequestBody UpdateEventUserRequest requestDto) {
        log.info("Updating event {} by user {}, by data {}", eventId, userId, requestDto);
        return service.updateEventOfUser(userId, eventId, requestDto);
        //+изменить можно только отмененные события или события в состоянии ожидания модерации (Ожидается код ошибки 409)
        //+дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента (Ожидается код ошибки 409)
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    //Получение информации о запросах на участие в событии текущего пользователя
    public Collection<ParticipationRequestDto> getRequestsForEventsOfUser(@PathVariable() Integer userId,
                                                                          @PathVariable() Integer eventId) {
        log.info("Get event {}, for user {}", eventId, userId);
        return service.getRequestsForEventsOfUser(userId, eventId);
        //В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
    }


    @PatchMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    //Изменение статуса (подтверждена, отменена)заявок на участие в событии
    public EventRequestStatusUpdateResult changeEventsRequestStatus(@PathVariable(required = false) Integer userId,
                                                                    @PathVariable(required = false) Integer eventId,
                                                                    @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        log.info("Change request of event {}  by user {}, to status {}", eventId, userId, updateRequest.getStatus());
        return service.changeEventsRequestStatus(userId, eventId, updateRequest);
        //если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
        //нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409)
        //статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409)
        //если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить
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
