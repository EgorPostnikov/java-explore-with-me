package ru.practicum.admin_api.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin_api.model.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Collection<User> getUsersByIdIsIn(List<Integer> ids,PageRequest pageRequest);
}
