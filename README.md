Weather Forecast Application [Java]
Overview

This project is a simple weather forecast application built in Java.
It allows users to input an address (or zip code) and view the current weather forecast.
The results are displayed via a user interface (UI), and recent results are cached to improve performance.

Requirements Implemented

✅ Built entirely in Java.

✅ Accepts an address or zip code as input.

✅ Retrieves forecast data for the given zip code (including current temperature, highs/lows, and extended forecast if available).

✅ Displays forecast details to the user via a UI.

✅ Caches forecast results for 30 minutes for subsequent requests with the same zip code.

✅ Displays an indicator in the UI if data is retrieved from the cache.

Assumptions

The project is open to interpretation; implementation details may vary.

Functionality is prioritized over visual design.

Partial completion is acceptable if time is limited.

How It Works

The user enters a zip code or address in the UI.

The application retrieves weather data from an external API (e.g., OpenWeatherMap or WeatherAPI).

The data is processed and displayed in the interface.

Results are cached locally for 30 minutes to optimize repeated lookups.

A visual indicator shows whether the data came from live API results or cache.
