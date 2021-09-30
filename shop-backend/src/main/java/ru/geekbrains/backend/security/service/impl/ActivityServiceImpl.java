package ru.geekbrains.backend.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.backend.security.domain.entity.ActivityEntity;
import ru.geekbrains.backend.security.repository.ActivityRepository;
import ru.geekbrains.backend.security.service.ActivityService;

import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    @Transactional
    public void save(ActivityEntity activity) {
        activityRepository.save(activity);
    }

    @Override
    public Optional<ActivityEntity> findActivityByUuid(String uuid) {
        return activityRepository.findByUuid(uuid);
    }

    @Override
    public int activate(String uuid) {
        return activityRepository.changeActive(uuid, true);
    }

    @Override
    public int deactivate(String uuid) {
        return activityRepository.changeActive(uuid, false);
    }

    @Override
    public Optional<ActivityEntity> findActivityByUserId(Long id) {
        return activityRepository.findByUserId(id);
    }
}
