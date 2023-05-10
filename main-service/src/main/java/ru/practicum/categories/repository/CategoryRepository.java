package ru.practicum.categories.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.categories.model.Category;

import java.util.Collection;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Collection<Category> findAllBy(PageRequest pageRequest);

    Boolean existsCategoryByNameIs(String name);

}
