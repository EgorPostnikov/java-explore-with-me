package ru.practicum;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.dto.StatsRequestDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class StatsClient {
    private static final Logger log = LoggerFactory.getLogger(StatsClient.class);
    private final String statsServiceUri;
    private final ObjectMapper json;
    private final HttpClient httpClient;

    public StatsClient(@Value("ewm-main-service") String application,
                       @Value("http://localhost:9090") String statsServiceUri,
                       ObjectMapper json) {
        this.statsServiceUri = statsServiceUri;
        this.json = json;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();
    }

    public void saveHit(HitDto hit) {
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

    public Collection<StatsDto> loadStats(StatsRequestDto requestDto) {
        Collection<StatsDto> stats = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String start = requestDto.getStart().format(formatter);
        String end = requestDto.getEnd().format(formatter);
        String unique = requestDto.getUnique().toString();
        String uris = requestDto.getUris().toString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(statsServiceUri + "/stats?start="+start+"&end="+end+"&uris="+uris+"&unique="+unique))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.ACCEPT, "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String jsonStats = response.body();
                if (!jsonStats.isBlank()) {
                    Gson gson = new Gson();
                    JsonArray jsonTaskArray = JsonParser.parseString(jsonStats).getAsJsonArray();
                    for (JsonElement jsonTask : jsonTaskArray) {
                        StatsDto stat = gson.fromJson(jsonTask, StatsDto.class);
                        stats.add(stat);
                    }
                    System.out.println("Значение по ключу успешно получено.");
                } return Collections.emptyList();


            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return stats;
    }
}