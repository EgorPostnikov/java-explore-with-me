package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.model.Hit;
import ru.practicum.model.HitMapper;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.StatsRequest;
import ru.practicum.storage.StatRepository;

@Service
@AllArgsConstructor
public class StatServiceImpl implements StatService {
    private static final Logger log = LoggerFactory.getLogger(StatServiceImpl.class);
    private final StatRepository repository;
    private final HitMapper mapper;
    @Override
    public HitDto createHit(HitDto hitDto){
        Hit hit= repository.save(mapper.toHit(hitDto));
        log.info("Hit with id #{} saved", hit.getId());
    return mapper.toHitDto(hit);
    }

    @Override
    public StatsDto viewStats(StatsRequest request) {

        return null;
    }

}
