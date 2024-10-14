# cloud-nine
A weather application that provides real-time weather data and forecasts

## Description

**cloud-nine** is a Java Spring Boot application that integrates with OpenWeatherMap APIs to provide real-time weather data and five-day forecasts for specific locations.

## Features

- Fetches current weather data and forecasts from OpenWeatherMap.
- Asynchronous database operations using R2DBC with MySQL.
- Spring WebFlux for non-blocking web requests.
- WebClient for non-blocking integration with OpenWeatherMap APIs.
- Flyway for managing database migrations.
- Redis for caching fetched data to improve performance.
- User account creation and JWT-based authentication for secure access.

## Technologies Used

- Java 17
- Spring Boot 3
- Gradle
- MySQL
- R2DBC
- Spring WebFlux
- WebClient
- Flyway
- Redis

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Kagimbz/cloud-nine.git
   ```
2. Navigate to the application directory:
   ```bash
   cd cloud-nine
   ```   
3. Set up the MySQL database and configure the application properties in `src/main/resources/application.properties` with database details for both R2DBC and Flyway.
4. Create an OpenWeatherMap account and add the provided API key to the application properties file above under the `open.weather.api_key` property.
5. Run the application using Gradle.
   ```bash
   ./gradlew bootRun
   ```
   
The application runs on port 1235.

Enjoy exploring the weather data!
