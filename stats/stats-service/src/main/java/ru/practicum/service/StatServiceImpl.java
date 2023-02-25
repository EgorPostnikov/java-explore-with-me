package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.model.Hit;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.Stats;
import ru.practicum.model.StatsRequest;
import ru.practicum.storage.StatRepository;

import java.util.Collection;

@Service
@Setter
@AllArgsConstructor
public class StatServiceImpl implements StatService {
    private static final Logger log = LoggerFactory.getLogger(StatServiceImpl.class);
    private final StatRepository repository;

    @Override
    public HitDto createHit(HitDto hitDto){
        Hit hit= repository.save(HitMapper.INSTANCE.toHit(hitDto));
        log.info("Hit with id #{} saved", hit.getId());
    return HitMapper.INSTANCE.toHitDto(hit);
    }

    @Override
    public Collection <StatsDto> viewStats(StatsRequest request) {
        Collection <Stats> stats = repository.getStatsUnique(request.getStart(),request.getEnd(),request.getUris());
        return StatsMapper.INSTANCE.toStatsDtos(stats);
    }

}
