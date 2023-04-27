package ru.practicum.event.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatsClient;
import ru.practicum.apiError.Response;
import ru.practicum.dto.HitDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PublicEventController {
    private final EventServiceImpl service;
    private final StatsClient statsClient;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventShortDto> getFullEventsInfo(@RequestParam() String text,
                                                       @RequestParam() List<Integer> categories,
                                                       @RequestParam() Boolean paid,
                                                       @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                       @RequestParam(defaultValue = "undefined") String rangeStart,
                                                       @RequestParam(defaultValue = "undefined") String rangeEnd,
                                                       @RequestParam(defaultValue = "id") String sort,
                                                       @RequestParam(defaultValue = "0") Integer from,
                                                       @RequestParam(defaultValue = "10") Integer size,
                                                       HttpServletRequest request) {
        if (sort.equals("EVENT_DATE")) {
            sort = "eventDate";
        }
        if (sort.equals("VIEWS")) {
            sort = "views";
        }
        PageRequest pageRequest = PageRequest.of(from, size, Sort.by(sort));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().minusYears(200L);
        if (!(rangeStart.equals("undefined") || rangeEnd.equals("undefined"))) {
            start = LocalDateTime.parse(rangeStart, formatter);
            end = LocalDateTime.parse(rangeEnd, formatter);
        }
        statsClient.saveHit(new HitDto(null,
                "ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().toString()));
        log.info("Get all events from {},size {}", from, size);
        return service.getShortEventsInfo(text, categories, paid, onlyAvailable, start, end, sort, pageRequest);
        //информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
        //информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getFullEvent(@PathVariable() Integer eventId,
                                     HttpServletRequest request) {
        log.info("Get event {}", eventId);
        statsClient.saveHit(new HitDto(null,
                "ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().toString()));
        return service.getFullEvent(eventId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Response handleException(ValidationException exception) {
        return new Response(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SecurityException.class)
    public Response handleException(RuntimeException exception) {
        return new Response(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Response handleException(NoSuchElementException exception) {
        return new Response(exception.getMessage());
    }
}
