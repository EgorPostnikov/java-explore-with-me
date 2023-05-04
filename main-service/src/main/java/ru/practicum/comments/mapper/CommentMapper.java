package ru.practicum.comments.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.model.Comment;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "authorId", expression = "java(userId)")
    Comment toComment(CommentDto entity, Integer userId);

    CommentDto toCommentDto(Comment entity);

    Collection<CommentDto> toCommentDtos(Collection<Comment> entities);

}
