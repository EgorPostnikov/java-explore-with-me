package ru.practicum;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

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

    public void saveHit() {
        HitDto hit = new HitDto(10L, application, statsServiceUri, null, LocalDateTime.now());

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

    public String loadStats() {
        String value = "";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(statsServiceUri + "/stat"))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.ACCEPT, "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                value = response.body();
                System.out.println("???????????????? ???? ?????????? ?????????????? ????????????????.");
            } else {
                System.out.println("??????-???? ?????????? ???? ??????. ???????????? ???????????? ?????? ??????????????????: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("???? ?????????? ???????????????????? ?????????????? ???????????????? ????????????.\n" +
                    "??????????????????, ????????????????????, ?????????? ?? ?????????????????? ??????????????.");
        }
        return value;
    }
}