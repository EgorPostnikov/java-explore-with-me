package ru.practicum.service;

import ru.practicum.dto.HitDto;
import ru.practicum.model.Hit;


public interface HitService {

    HitDto createHit (HitDto hitDto);
}
