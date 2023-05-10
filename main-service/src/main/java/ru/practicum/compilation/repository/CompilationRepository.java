package ru.practicum.compilation.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilation.model.Compilation;

import java.util.Collection;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    Collection<Compilation> getCompilationsByPinnedIs(Boolean pinned, PageRequest pageRequest);
}
