package ru.practicum.categories.comments.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentCountByEvent {
    private Integer eventId;
    private Long commentsCount;

}

