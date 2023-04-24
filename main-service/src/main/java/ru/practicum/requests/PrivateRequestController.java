package ru.practicum.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.apiError.Response;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PrivateRequestController {

    private final RequestServiceImpl service;

    @GetMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    //получение информации о заявках текущего пользователя на участие в чужих событиях
    public Collection<ParticipationRequestDto> getRequest(@PathVariable Integer userId,
                                                          @RequestParam(defaultValue = "0") Integer from,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        log.info("Get Request from user {}", userId);
        return service.getUserRequests(userId, pageRequest);
        //В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
    }

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    //Добавление запроса от текущего пользователя на участие в событии
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
        //нельзя добавить повторный запрос (Ожидается код ошибки 409)
        //инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
        //нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
        //если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
        //если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
    }


    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    //Отмена своего запроса на участие в событии
    public ParticipationRequestDto cancellRequest(@PathVariable Integer userId,
                                                  @PathVariable Integer requestId) {
        log.info("Cancelling Request # {} from user {}", requestId, userId);

        return service.cancellRequest(userId, requestId);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Response handleException(ValidationException exception) {
        return new Response(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RuntimeException.class)
    public Response handleException(RuntimeException exception) {
        return new Response(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Response handleException(NoSuchElementException exception) {
        return new Response(exception.getMessage());
    }

}
