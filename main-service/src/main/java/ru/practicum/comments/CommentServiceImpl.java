package ru.practicum.comments;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Override
    public CommentDto createComment(CommentDto requestDto) {
        return null;
    }

    @Override
    public void deleteComment(Integer comId) {
    }

    @Override
    public CommentDto updateComment(Integer comId, CommentDto requestDto) {
        return null;
    }

    @Override
    public CommentDto getComment(Integer comId) {
        return null;
    }

    @Override
    public Collection<CommentDto> getComments(PageRequest pageRequest) {
        return null;
    }
}
