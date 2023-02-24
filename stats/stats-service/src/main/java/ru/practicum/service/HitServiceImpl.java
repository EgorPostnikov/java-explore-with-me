package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.model.Hit;
import ru.practicum.model.HitMapper;
import ru.practicum.storage.HitRepository;

@Service
@AllArgsConstructor
public class HitServiceImpl implements HitService{
    private static final Logger log = LoggerFactory.getLogger(HitServiceImpl.class);
    HitRepository repository;
    HitMapper mapper;
    @Override
    public HitDto createHit(HitDto hitDto){
        Hit hit= repository.save(mapper.toHit(hitDto));
        log.info("Hit with id #{} saved", hit.getId());
    return mapper.toHitDto(hit);
    }

}
