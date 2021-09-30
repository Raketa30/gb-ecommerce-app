package ru.geekbrains.backend.security.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.backend.security.domain.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    @Query("select case when count(u) >0 then true else false end from UserEntity u where lower(u.email) = lower(:email)")
    boolean existByEmail(@Param("email") String email);

    @Query("select case when count(u) >0 then true else false end from UserEntity u where lower(u.username) = lower(:username)")
    boolean existByUsername(@Param("username") String username);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.password = :password WHERE u.email = :email")
    int updatePassword(@Param("password") String password, @Param("email") String email);
}
