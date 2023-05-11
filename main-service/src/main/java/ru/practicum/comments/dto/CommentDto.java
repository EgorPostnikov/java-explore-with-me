package ru.practicum.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Integer id;
    @Size(min = 1, max = 512)
    @NotBlank
    private String text;
    private Integer eventId;
    private String authorName;
    private LocalDateTime created;
    private Boolean redacted;
}