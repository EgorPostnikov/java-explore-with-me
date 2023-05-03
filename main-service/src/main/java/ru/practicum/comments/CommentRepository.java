package ru.practicum.comments;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.categories.model.Category;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {


}
