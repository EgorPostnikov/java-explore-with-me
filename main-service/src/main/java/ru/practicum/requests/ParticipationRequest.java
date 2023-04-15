package ru.practicum.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests", schema = "public")
public class ParticipationRequest {
    private String created;
    @Column(name = "event_id")
    private Integer event;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "requester_id")
    private Integer requester;
    private String status;
}
