package ru.practicum.user;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    Collection<User> getUsersByIdIn(Collection<Integer> ids,PageRequest pageRequest);
    Collection<User> getAllBy(PageRequest pageRequest);

}
