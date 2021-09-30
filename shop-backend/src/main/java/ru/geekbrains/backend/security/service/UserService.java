package ru.geekbrains.backend.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.geekbrains.backend.security.domain.entity.UserEntity;


public interface UserService extends UserDetailsService {
    boolean checkUserExist(String username, String email);

    void save(UserEntity user);

    int activate(String uuid);

    int updatePassword(String password, String email);

    void activate(UserEntity user);

    void resetPassword(UserEntity user, String resetToken);
}
