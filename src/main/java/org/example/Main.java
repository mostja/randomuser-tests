package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.SECONDS;

public class Main {

    private static final String API_URL = "https://randomuser.me/api/?results=100";
    private static final Duration TIMEOUT = Duration.of(10, SECONDS);

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpResponse<String> response = sendHttpRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        UserResponse userResponse = objectMapper.readValue(response.body(), UserResponse.class);

        List<Result> filteredResults = filterUsersByCharAndSort(userResponse.getResults(), 'S');
        filteredResults.forEach(System.out::println);
    }

    public static List<Result> filterUsersByCharAndSort(List<Result> results, char c) {
        Objects.requireNonNull(results, "Input must not be null");

        return results.stream()
                .filter(Objects::nonNull)
                .filter(result -> startsWithChar(result.getName().getFirst(), c))
                .sorted((r1, r2) -> r1.getName().getFirst().compareToIgnoreCase(r2.getName().getFirst()))
                .toList();
    }

    private static boolean startsWithChar(String name, char c) {
        return name != null && !name.isEmpty() && name.charAt(0) == c;
    }

    private static HttpResponse<String> sendHttpRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .timeout(TIMEOUT)
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
