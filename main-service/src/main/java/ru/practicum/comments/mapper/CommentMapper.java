package ru.practicum.comments.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.dto.UpdateCommentDto;
import ru.practicum.comments.model.Comment;
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
