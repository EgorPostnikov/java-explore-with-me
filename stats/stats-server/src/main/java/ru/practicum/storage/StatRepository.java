package ru.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Hit;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Hit, Long> {

    @Query(name = "ViewStatsUris", nativeQuery = true)
    Collection<Stats> viewStatsUris(List<String> uris, LocalDateTime start, LocalDateTime end);

    @Query(name = "ViewStatsUniqueUris", nativeQuery = true)
    Collection<Stats> viewStatsUniqueUris(List<String> uris, LocalDateTime start, LocalDateTime end);

    @Query(name = "ViewStats", nativeQuery = true)
    Collection<Stats> viewStats(LocalDateTime start, LocalDateTime end);

    @Query(name = "ViewStatsUnique", nativeQuery = true)
    Collection<Stats> viewStatsUnique(LocalDateTime start, LocalDateTime end);
}

