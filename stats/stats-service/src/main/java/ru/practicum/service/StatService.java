package ru.practicum.service;

import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.StatsRequest;


public interface StatService {

    HitDto createHit (HitDto hitDto);

    StatsDto viewStats(StatsRequest request);
}
