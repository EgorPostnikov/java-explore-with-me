package ru.practicum.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.categories.CategoryDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events", schema = "public")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String annotation;
    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")*/
    private Integer category;
    private String description;
    @Column(name = "event_date")
    private String eventDate;
    /*@OneToOne
    @JoinColumn(name = "location_id")
    private Location location;*/
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    private String title;
    //@OneToOne
    //@JoinColumn(name = "id")
    @Column(name = "creator_id")
    private Integer creatorId;
}
