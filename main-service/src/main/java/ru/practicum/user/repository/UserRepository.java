package ru.practicum.user.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.model.User;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Integer> {

    Collection<User> getUsersByIdIn(Collection<Integer> ids, PageRequest pageRequest);

    Collection<User> getAllBy(PageRequest pageRequest);

    Boolean existsUserByName(String name);

}
