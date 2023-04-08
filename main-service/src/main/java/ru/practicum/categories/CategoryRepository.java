package ru.practicum.categories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.event.Event;
import ru.practicum.user.User;

import java.util.Collection;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Collection<Category> findAllBy(PageRequest pageRequest);
    Boolean existsCategoryByNameIs(String name);

}
