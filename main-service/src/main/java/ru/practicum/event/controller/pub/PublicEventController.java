package ru.practicum.event.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatsClient;
import ru.practicum.dto.HitDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


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
    public Collection<EventShortDto> getShortEventsInfo(@RequestParam(defaultValue = "") String text,
                                                        @RequestParam(required = false) List<Integer> categories,
                                                        @RequestParam(defaultValue = "false") Boolean paid,
                                                        @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                        @RequestParam(defaultValue = "undefined") String rangeStart,
                                                        @RequestParam(defaultValue = "undefined") String rangeEnd,
                                                        @RequestParam(defaultValue = "EVENT_DATE") String sort,//было id
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
        Collection<EventShortDto> entities;
        if (categories == null) {
            entities = Collections.emptyList();
        } else {
            entities = service
                    .getShortEventsInfo(text, categories, paid, onlyAvailable, start, end, sort, pageRequest);
        }
        statsClient.saveHit(new HitDto(null,
                "ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().toString()));
        log.info("Get all events from {},size {}, with {}", from, size, categories);
        return entities;
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getFullEvent(@PathVariable() Integer eventId,
                                     HttpServletRequest request) {
        log.info("Get event {}", eventId);
        EventFullDto entity = service.getFullEvent(eventId);
        statsClient.saveHit(new HitDto(null,
                "ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().toString()));
        return entity;
    }

}
