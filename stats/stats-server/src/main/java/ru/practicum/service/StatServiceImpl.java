package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.model.Hit;
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
    private final HitMapper hitMapper;
    private final StatsMapper statsMapper;

    @Override
    public HitDto createHit(HitDto hitDto) {
        Hit entity = hitMapper.toHit(hitDto);
        Hit hit = repository.save(entity);
        log.info("Hit with id #{} saved", hit.getId());
        return hitMapper.toHitDto(hit);
    }

    @Override
    public Collection<StatsDto> viewStats(StatsRequest request) {
        Collection<Stats> stats;
        if (request.getUnique()) {
            if (request.getUris() == null) {
                stats = repository.viewStatsUnique(request.getStart(), request.getEnd());
            } else {
                stats = repository.viewStatsUniqueUris(request.getUris(), request.getStart(), request.getEnd());
            }
        } else {
            if (request.getUris() == null) {
                stats = repository.viewStats(request.getStart(), request.getEnd());
            } else {
                stats = repository.viewStatsUris(request.getUris(), request.getStart(), request.getEnd());
            }
        }
        log.info("List of hits got, hits qty is - {} ", stats.size());
        return statsMapper.toStatsDtos(stats);
    }

}
