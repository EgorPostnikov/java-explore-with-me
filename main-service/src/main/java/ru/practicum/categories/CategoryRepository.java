package ru.practicum.categories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.Event;
import ru.practicum.user.User;

import java.util.Collection;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


}
