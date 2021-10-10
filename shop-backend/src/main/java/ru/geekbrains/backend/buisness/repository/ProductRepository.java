package ru.geekbrains.backend.buisness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.backend.buisness.domain.entity.ProductEntity;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByTitle(String title);
}
