package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hits")
@NamedNativeQuery(name = "ViewStatsUris",
        query = "SELECT s.app, s.uri, count(s.ip) AS qty FROM hits AS s" +
                " WHERE s.uri IN ?1 AND s.timestamp >=?2 AND s.timestamp <=?3 " +
                "GROUP BY s.app, s.uri ORDER BY qty DESC",
        resultSetMapping = "StatsDtoMapping")
@NamedNativeQuery(name = "ViewStatsUniqueUris",
        query = "SELECT s.app, s.uri, count(DISTINCT s.ip) AS qty FROM hits AS s" +
                " WHERE s.uri IN ?1 AND s.timestamp >=?2 AND s.timestamp <=?3 " +
                "GROUP BY s.app, s.uri ORDER BY qty DESC",
        resultSetMapping = "StatsDtoMapping")
@NamedNativeQuery(name = "ViewStats",
        query = "SELECT s.app, s.uri, count(s.ip) AS qty FROM hits AS s" +
                " WHERE  s.timestamp >=?1 AND s.timestamp <=?2 " +
                "GROUP BY s.app, s.uri ORDER BY qty DESC",
        resultSetMapping = "StatsDtoMapping")
@NamedNativeQuery(name = "ViewStatsUnique",
        query = "SELECT s.app, s.uri, count(DISTINCT s.ip) AS qty FROM hits AS s" +
                " WHERE  s.timestamp >=?1 AND s.timestamp <=?2 " +
                "GROUP BY s.app, s.uri ORDER BY qty DESC",
        resultSetMapping = "StatsDtoMapping")
@SqlResultSetMapping(name = "StatsDtoMapping",
        classes = {@ConstructorResult(columns = {
                @ColumnResult(name = "app", type = String.class),
                @ColumnResult(name = "uri", type = String.class),
                @ColumnResult(name = "qty", type = Long.class)},
                targetClass = Stats.class)
        }
)
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
