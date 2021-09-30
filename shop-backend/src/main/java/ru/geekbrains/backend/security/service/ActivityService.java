package ru.geekbrains.backend.security.service;

import ru.geekbrains.backend.security.domain.entity.ActivityEntity;

import java.util.Optional;

public interface ActivityService {
    void save(ActivityEntity activity);

    Optional<ActivityEntity> findActivityByUuid(String uuid);

    int activate(String uuid);

    int deactivate(String uuid);

    Optional<ActivityEntity> findActivityByUserId(Long id);
}
