package com.example.demo.service;

import com.example.demo.model.WeatherResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class WeatherClient {

    @Value("${weather.apiKey}")
    private String apiKey;

    @Value("${weather.baseUrl}")
    private String baseUrl;

    @Value("${weather.units}")
    private String units;

    private final WebClient webClient = WebClient.create();

    public WeatherResult getByZip(String zip) {
        Map<?, ?> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.openweathermap.org")
                        .path("/data/2.5/weather")
                        .queryParam("zip", zip + ",US")
                        .queryParam("appid", apiKey)
                        .queryParam("units", units)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null) return null;

        Map<?, ?> main = (Map<?, ?>) response.get("main");
        List<Map<?, ?>> weatherList = (List<Map<?, ?>>) response.get("weather");

        WeatherResult result = new WeatherResult();
        result.setLocationName((String) response.get("name"));
        result.setTemperature(toDouble(main.get("temp")));
        result.setHigh(toDouble(main.get("temp_max")));
        result.setLow(toDouble(main.get("temp_min")));

        if (weatherList != null && !weatherList.isEmpty()) {
            result.setDescription((String) weatherList.get(0).get("description"));
            String icon = (String) weatherList.get(0).get("icon");
            result.setIconUrl("https://openweathermap.org/img/wn/" + icon + "@2x.png");
        }

        return result;
    }

    private Double toDouble(Object o) {
        return o == null ? null : ((Number) o).doubleValue();
    }
}
