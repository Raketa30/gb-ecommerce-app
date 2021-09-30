package ru.geekbrains.backend.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.backend.security.domain.UserDetailsImpl;
import ru.geekbrains.backend.security.domain.entity.ActivityEntity;
import ru.geekbrains.backend.security.domain.entity.RoleEntity;
import ru.geekbrains.backend.security.domain.entity.UserEntity;
import ru.geekbrains.backend.security.exception.RoleNotFoundException;
import ru.geekbrains.backend.security.exception.UserAlreadyActivatedException;
import ru.geekbrains.backend.security.repository.UserRepository;
import ru.geekbrains.backend.security.service.ActivityService;
import ru.geekbrains.backend.security.service.EmailService;
import ru.geekbrains.backend.security.service.RoleService;
import ru.geekbrains.backend.security.service.UserService;

import java.util.Collections;
import java.util.UUID;

import static ru.geekbrains.backend.constants.NameConstant.DEFAULT_ROLE;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ActivityService activityService;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, ActivityService activityService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.activityService = activityService;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new UserDetailsImpl(user);
    }

    @Override
    public boolean checkUserExist(String username, String email) {
        return userRepository.existByEmail(email) || userRepository.existByUsername(username);
    }

    @Override
    @Transactional
    public void save(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        RoleEntity userRole = roleService.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new RoleNotFoundException("Default user role not found"));

        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        ActivityEntity activity = new ActivityEntity();
        activity.setUser(user);
        activity.setUuid(UUID.randomUUID().toString());

        emailService.sendActivationEmail(user.getEmail(), user.getUsername(), activity.getUuid());

        activityService.save(activity);
    }

    @Override
    public int activate(String uuid) {
        ActivityEntity activity = activityService.findActivityByUuid(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("activity not found with uuid: " + uuid));

        if (activity.isActivated()) {
            throw new UserAlreadyActivatedException("User Already activated");
        }

        return activityService.activate(uuid);
    }

    @Override
    @Transactional
    public int updatePassword(String password, String email) {
        return userRepository.updatePassword(passwordEncoder.encode(password), email);
    }

    @Override
    public void activate(UserEntity user) {
        ActivityEntity activity = activityService.findActivityByUserId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Activity Not Found with user: " + user.getUsername()));

        if (activity.isActivated())
            throw new UserAlreadyActivatedException("User already activated: " + user.getUsername());

        emailService.sendActivationEmail(user.getEmail(), user.getUsername(), activity.getUuid());
    }

    @Override
    public void resetPassword(UserEntity user, String resetToken) {
        emailService.sendResetPasswordEmail(user.getEmail(), resetToken);
    }
}
