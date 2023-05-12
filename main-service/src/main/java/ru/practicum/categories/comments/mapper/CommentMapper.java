package ru.practicum.categories.comments.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.categories.comments.dto.CommentDto;
import ru.practicum.categories.comments.dto.NewCommentDto;
import ru.practicum.categories.comments.dto.UpdateCommentDto;
import ru.practicum.categories.comments.model.Comment;
import ru.practicum.user.model.User;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "author", expression = "java(user)")
    Comment toComment(NewCommentDto entity, User user);

    Comment toComment(UpdateCommentDto entity);

    @Mapping(target = "authorName", source = "author.name")
    CommentDto toCommentDto(Comment entity);

    Collection<CommentDto> toCommentDtos(Collection<Comment> entities);

}
