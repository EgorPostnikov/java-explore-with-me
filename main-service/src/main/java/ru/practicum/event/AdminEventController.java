package ru.practicum.event;

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
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminEventController {
    private final CEventServiceImpl service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    //Поиск событий
    public Collection<EventFullDto> getFullEventsInfo(@RequestParam(required = false, defaultValue = "0") List<Integer> users,
                                                      @RequestParam(required = false, defaultValue = "0") List<String> states,
                                                      @RequestParam(required = false, defaultValue = "0") List<Integer> categories,
                                                      @RequestParam(required = false, defaultValue = "undefined") String rangeStart,
                                                      @RequestParam(required = false, defaultValue = "undefined") String rangeEnd,
                                                      @RequestParam(defaultValue = "0") Integer from,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.unsorted());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().minusYears(200L);
        if (!(rangeStart.equals("undefined") || rangeEnd.equals("undefined"))) {
            start = LocalDateTime.parse(rangeStart, formatter);
            end = LocalDateTime.parse(rangeEnd, formatter);
        }
        log.info("Get all events from {},size {}, for users {}", from, size, users.toString());
        return service.getFullEventsInfo(users, states, categories, start, end, pageRequest);
        //В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    //Редактирование данных события и его статуса(отклонение/публикация)
    public EventFullDto updateEvent(@PathVariable() Integer eventId,
                                    @RequestBody UpdateEventAdminRequest requestDto) {
        log.info("Updating event {}, by data {}", eventId, requestDto);
        return service.updateEvent(eventId, requestDto);
        //дата начала изменяемого события должна быть не ранее чем за час от даты публикации. (Ожидается код ошибки 409)
        //событие можно публиковать, только если оно в состоянии ожидания публикации (Ожидается код ошибки 409)
        //событие можно отклонить, только если оно еще не опубликовано (Ожидается код ошибки 409)
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
