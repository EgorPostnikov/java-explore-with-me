package ru.practicum;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class StatsClient {
    private static final Logger log = LoggerFactory.getLogger(StatsClient.class);
    private final String application;
    private final String statsServiceUri;
    private final ObjectMapper json;
    private final HttpClient httpClient;

    public StatsClient(@Value("ewm-main-service") String application,
                       @Value("services.stats-service.uri:http://localhost:9090") String statsServiceUri,
                       ObjectMapper json) {
        this.application = application;
        this.statsServiceUri = statsServiceUri;
        this.json = json;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();
    }

    public void hit() {
        HitDto hit = new HitDto();
        hit.setApp(application);

        try {
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest
                    .BodyPublishers
                    .ofString(json.writeValueAsString(hit));

            HttpRequest hitRequest = HttpRequest.newBuilder()
                    .uri(URI.create(statsServiceUri + "/hit"))
                    .POST(bodyPublisher)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .build();
            HttpResponse<Void> response = httpClient.send(hitRequest, HttpResponse.BodyHandlers.discarding());


        } catch (Exception e) {
            log.warn("Cannot record hit", e);
        }
    }

    public void stats() {
        StatsDto stats = new StatsDto();
        stats.setApp(application);

        try {
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest
                    .BodyPublishers
                    .ofString(json.writeValueAsString(stats));

            HttpRequest statsRequest = HttpRequest.newBuilder()
                    .uri(URI.create(statsServiceUri + "/stats"))
                    .GET()
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .build();
            HttpResponse<Void> response = httpClient.send(statsRequest, HttpResponse.BodyHandlers.discarding());


        } catch (Exception e) {
            log.warn("Cannot record hit", e);
        }
    }
}