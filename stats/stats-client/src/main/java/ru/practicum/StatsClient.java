package ru.practicum;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.time.Duration;

@Service
public class StatsClient {
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
   /* public void hit(){
        HitDto hit = new HitDto();
        hit.setApp(application);

        try{
            HttpRequest.BodyPublisher bodyPublisher= HttpRequest
                    .BodyPublishers
                    .ofString(json.writeValueAsString(hit));

            HttpRequest hitRequest= HttpRequest.newBuilder()
                .uri(URI.create(statsServiceUri+"/hit"))
                    .POST(bodyPublisher)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json");


    } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }*/
}