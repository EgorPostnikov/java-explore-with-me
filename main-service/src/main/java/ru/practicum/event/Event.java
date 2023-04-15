package ru.practicum.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.categories.Category;
import ru.practicum.categories.CategoryDto;
import ru.practicum.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events", schema = "public")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String annotation;
    private String description;
    @Column(name = "event_date")
    private String eventDate;
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    private User initiator;
    private Integer views;
    @Column(name = "published_on")
    private String publishedOn = LocalDateTime.now().toString();
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "state_name")
    private String state = "PENDING";
    @Column(name = "created_on")
    private String createdOn = LocalDateTime.now().toString();
    /*@ElementCollection
    @CollectionTable(name="states", joinColumns=@JoinColumn(name="event_id"))
    @Column(name="name")
    private Set<String> state = new HashSet<>();*/
}
