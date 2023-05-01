package ru.practicum.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.StatsRequest;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class StatsServiceController {
    private final StatService service;

    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto createHit(@RequestBody HitDto entity) {
        return service.createHit(entity);
    }

    @SneakyThrows
    @GetMapping(path = "/stats")
    @ResponseStatus(HttpStatus.OK)
    public Collection<StatsDto> viewStats(@RequestParam(defaultValue = "2020-05-05 00:00:00") String start,
                                          @RequestParam(defaultValue = "2035-05-05 00:00:00") String end,
                                          @RequestParam(required = false) List<String> uris,
                                          @RequestParam(defaultValue = "false") Boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return service.viewStats(new StatsRequest(
                LocalDateTime.parse(start, formatter),
                LocalDateTime.parse(end, formatter),
                uris,
                unique));
    }
}
