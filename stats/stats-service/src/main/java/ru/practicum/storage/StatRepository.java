package ru.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Hit;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface StatRepository extends JpaRepository<Hit, Long> {

    @Query(" Select s.app,s.uri,count(s.id) from Stats s where s.uri= ?3")
    Collection<Stats> getStatsUnique(LocalDateTime start,LocalDateTime end, Collection <String> uris);
}

