package ru.geekbrains.backend.security.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.backend.security.domain.UserDetailsImpl;
import ru.geekbrains.backend.security.domain.entity.UserEntity;
import ru.geekbrains.backend.security.exception.UserExistsException;
import ru.geekbrains.backend.security.objects.JsonException;
import ru.geekbrains.backend.security.service.UserService;
import ru.geekbrains.backend.security.util.CookieUtils;
import ru.geekbrains.backend.security.util.JwtUtils;

import javax.validation.Valid;

import static ru.geekbrains.backend.constants.NameConstant.AUTH;


@RestController
@RequestMapping(AUTH)
@Log
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, CookieUtils cookieUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.cookieUtils = cookieUtils;
    }

    @PostMapping("/test-no-auth")
    public String testNoAuth() {
        return "OK-no-auth";
    }

    @PostMapping("/test-with-auth")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public String testWithAuth() {
        return "OK-with-auth";
    }

    @PutMapping("/register")
    public ResponseEntity register(@Valid @RequestBody UserEntity userEntity) {
        if (userService.checkUserExist(userEntity.getUsername(), userEntity.getEmail())) {
            throw new UserExistsException("User with current username or email exist");
        }

        userService.save(userEntity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/activate-account")
    public ResponseEntity<Boolean> activate(@RequestBody String uuid) {
        int updateCount = userService.activate(uuid);

        return ResponseEntity.ok(updateCount == 1);
    }

    @PostMapping("/resend-activate-email")
    public ResponseEntity resendActivateEmail(@RequestBody String usernameOrEmail) {
        UserDetailsImpl user = (UserDetailsImpl) userService.loadUserByUsername(usernameOrEmail);
        userService.activate(user.getUser());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserEntity> login(@Valid @RequestBody UserEntity user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.isActivated()) {
            throw new DisabledException("User disabled");
        }

        String jwtToken = jwtUtils.createAccessToken(userDetails.getUser());
        HttpCookie cookie = cookieUtils.createJwtCookie(jwtToken);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(userDetails.getUser());
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity logout() {
        HttpCookie cookie = cookieUtils.deleteCookie();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .build();
    }

    @PostMapping("/update-password")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Boolean> updatePassword(@RequestBody String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        int updateCount = userService.updatePassword(password, user.getUser().getEmail());

        return ResponseEntity.ok(updateCount == 1);
    }

    @PostMapping("/send-reset-password-email")
    public ResponseEntity sendEmailResetPassword(@RequestBody String email) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(email);

        if (userDetails != null) {
            UserEntity user = userDetails.getUser();
            userService.resetPassword(user, jwtUtils.createResetToken(user));
        }

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonException> handleException(Exception e) {
        return new ResponseEntity<>(new JsonException(e.getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
    }
}
