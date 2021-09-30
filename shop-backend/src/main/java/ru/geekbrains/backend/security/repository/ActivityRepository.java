package ru.geekbrains.backend.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.backend.security.domain.entity.ActivityEntity;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE ActivityEntity a SET a.activated = :active WHERE a.uuid = :uuid")
    int changeActive(@Param("uuid") String uuid, @Param("active") boolean active);

    Optional<ActivityEntity> findByUserId(Long id);

    Optional<ActivityEntity> findByUuid(String uuid);

}
