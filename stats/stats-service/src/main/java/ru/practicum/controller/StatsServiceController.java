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
    HitService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto createBooking( @RequestBody HitDto entity)  {
        return service.createHit(entity);
    }
}
