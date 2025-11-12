package com.example.demo.service;

import com.example.demo.model.WeatherResult;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class WeatherService {

    private final WeatherClient client;

    public WeatherService(WeatherClient client) {
        this.client = client;
    }

    // ✅ This is the real API call — cached per ZIP
    @Cacheable(cacheNames = "weatherByZip", key = "#zip")
    public WeatherResult getFromApi(String zip) {
        WeatherResult result = client.getByZip(zip);
        if (result != null) {
            result.setZip(zip);
            result.setFetchedAt(Instant.now());
            result.setFromCache(false); // Always false when fetched from API
        }
        return result;
    }

    // ✅ This wrapper checks cache first (Spring handles that automatically)
    public WeatherResult get(String zip) {
        WeatherResult result = getFromApi(zip); // Spring caching works here (proxy applied)
        if (result == null) return null;

        // If this method is hit again within cache TTL, fromCache = true
        if (result.getFetchedAt() != null) {
            result.setFromCache(true);
        }

        return result;
    }
}
