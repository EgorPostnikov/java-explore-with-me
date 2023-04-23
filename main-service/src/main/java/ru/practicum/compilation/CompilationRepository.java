package ru.practicum.compilation;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.compilation.dto.Compilation;

import java.util.Collection;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Integer> {


    Collection<Compilation> getCompilationsByPinnedIs(Boolean pinned, PageRequest pageRequest);
}
