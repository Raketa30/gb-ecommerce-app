package ru.geekbrains.backend.security.service;

import ru.geekbrains.backend.security.domain.entity.RoleEntity;

import java.util.Optional;

public interface RoleService {
    Optional<RoleEntity> findByName(String name);
}
