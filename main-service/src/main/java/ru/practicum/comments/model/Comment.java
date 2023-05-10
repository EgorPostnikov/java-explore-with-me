package ru.practicum.comments.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
    private Boolean updated;
}