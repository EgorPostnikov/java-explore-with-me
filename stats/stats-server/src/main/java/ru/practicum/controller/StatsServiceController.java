package ru.practicum.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.StatsRequest;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
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

    @GetMapping(path = "/stats")
    @ResponseStatus(HttpStatus.OK)
    public Collection<StatsDto> viewStats(@RequestParam LocalDateTime start,
                                          @RequestParam LocalDateTime end,
                                          @RequestParam List<String> uris,
                                          @RequestParam(defaultValue = "false") Boolean unique) {
        return service.viewStats(new StatsRequest(start, end, uris, unique));
    }
}
