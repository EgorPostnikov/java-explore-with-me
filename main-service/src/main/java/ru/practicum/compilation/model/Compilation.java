package ru.practicum.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compilations", schema = "public")
public class Compilation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "pinned")
    private Boolean pinned;
    @Column(name = "title")
    private String title;
    @ElementCollection
    @CollectionTable(name = "sets", joinColumns = @JoinColumn(name = "compilation_id"))
    @Column(name = "event_id")
    private List<Integer> eventsId = new ArrayList<>();

/*@ElementCollection
    @CollectionTable(name="states", joinColumns=@JoinColumn(name="event_id"))
    @Column(name="name")
    private Set<String> state = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    */

}
