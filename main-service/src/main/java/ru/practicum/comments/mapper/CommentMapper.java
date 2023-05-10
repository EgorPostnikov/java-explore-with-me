package ru.practicum.comments.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.dto.UpdateCommentDto;
import ru.practicum.comments.model.Comment;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "authorId", expression = "java(userId)")
    Comment toComment(NewCommentDto entity, Integer userId);

    Comment toComment(UpdateCommentDto entity);

    NewCommentDto toCommentDto(Comment entity);

    Collection<NewCommentDto> toCommentDtos(Collection<Comment> entities);

}
