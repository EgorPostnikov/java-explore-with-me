package ru.practicum.comments.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments", schema = "public")
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer id;
    private String text;
    @Column(name = "event_id")
    private Integer eventId;
    @Column(name = "author_id")
    private Integer authorId;
    private LocalDateTime created = LocalDateTime.now();
}