package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.service.HitService;

@RestController
@RequestMapping(path = "/hit")
@RequiredArgsConstructor
public class StatsServiceController {
    private final HitService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto createHit( @RequestBody HitDto entity)  {
        return service.createHit(entity);
    }
}
