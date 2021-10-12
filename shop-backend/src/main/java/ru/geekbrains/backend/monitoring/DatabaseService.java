package ru.geekbrains.backend.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseService implements HealthIndicator {
    private final String DATABASE_SERVICE = "Database Service";

    @Override
    public Health health() {
        if (getDatabaseHEalth()) {
            return Health.up().withDetail(DATABASE_SERVICE, "Service is running").build();
        }
        return Health.down().withDetail(DATABASE_SERVICE, "Service is not available").build();

    }

    private boolean getDatabaseHEalth() {
        //logic
        return true;
    }
}
