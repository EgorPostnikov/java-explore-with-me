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
@RequestMapping(path = "events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class APublicEventController {
    private final CEventServiceImpl service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    //Получение событий с возможностью фильтрации
    public Collection<EventShortDto> getFullEventsInfo(@RequestParam() String text,
                                                       @RequestParam() List<Integer> categories,
                                                       @RequestParam() Boolean paid,
                                                       @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                       @RequestParam(defaultValue = "undefined") String rangeStart,
                                                       @RequestParam(defaultValue = "undefined") String rangeEnd,
                                                       @RequestParam(defaultValue = "id") String sort,
                                                       @RequestParam(defaultValue = "0") Integer from,
                                                       @RequestParam(defaultValue = "10") Integer size) {
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
        log.info("Get all events from {},size {}, for users {}", from, size);
        return service.getShortEventsInfo(text, categories, paid, onlyAvailable, start, end, sort, pageRequest);
        //+это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
        //+текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
        //+если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут позже текущей даты и времени
        //+информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
        //информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
        //+В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    //Получение подробной информации об опубликованном событии по его идентификатору
    public EventFullDto getFullEvent(@PathVariable() Integer eventId) {
        log.info("Get event {}", eventId);
        return service.getFullEvent(eventId);
        //В случае, если события с заданным id не найдено, возвращает статус код 404
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
