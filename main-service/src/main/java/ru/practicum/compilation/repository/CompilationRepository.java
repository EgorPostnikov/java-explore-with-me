package ru.practicum.compilation.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.compilation.model.Compilation;

import java.util.Collection;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    Collection<Compilation> getCompilationsByPinnedIs(Boolean pinned, PageRequest pageRequest);
}
