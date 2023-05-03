package ru.practicum.comments;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Integer id;
    private String text;
    private Integer eventId;
    private Integer authorId;
    private LocalDateTime created = LocalDateTime.now();
}