package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.StatsRequest;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatsServiceController {
    private final StatService service;

    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto createHit( @RequestBody HitDto entity)  {
        return service.createHit(entity);
    }

    @GetMapping(path = "/stats")
    @ResponseStatus(HttpStatus.OK)
    public StatsDto viewStats(@RequestParam (name = "start")LocalDateTime start,
                             @RequestParam (name = "end")LocalDateTime end,
                             @RequestParam (name = "uris") Collection<String> uris,
                             @RequestParam (name = "unique", defaultValue = "false") Boolean unique)  {
        StatsRequest request = new StatsRequest(start,end,uris,unique);
        return service.viewStats(request);
    }
}
