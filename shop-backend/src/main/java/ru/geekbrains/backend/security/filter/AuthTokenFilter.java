package ru.geekbrains.backend.security.filter;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.geekbrains.backend.security.domain.UserDetailsImpl;
import ru.geekbrains.backend.security.domain.entity.UserEntity;
import ru.geekbrains.backend.security.exception.JwtCommonException;
import ru.geekbrains.backend.security.util.CookieUtils;
import ru.geekbrains.backend.security.util.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ru.geekbrains.backend.constants.NameConstant.AUTH;


@Component
@Log
public class AuthTokenFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private JwtUtils jwtUtils;
    private CookieUtils cookieUtils;

    private List<String> permitURL = Arrays.asList(
            AUTH + "/register",
            AUTH + "/login",
            AUTH + "/activate-account",
            AUTH + "/test-no-auth",
            AUTH + "/send-reset-password-email",
            AUTH + "/resend-activate-email",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/**",
            "/swagger-ui.html",
            "/webjars/**"
    );

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Autowired
    public void setCookieUtils(CookieUtils cookieUtils) {
        this.cookieUtils = cookieUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //проверяем запрос на публичный адрес
        boolean isRequestToPublicAPI = permitURL.stream()
                .anyMatch(s -> request.getRequestURI().toLowerCase().contains(s));

        if (!isRequestToPublicAPI
//                && !request.getMethod().equals(HttpMethod.OPTIONS.toString())
        ) {
            String jwt = null;

            if (request.getRequestURI().contains("update-password")) {
                jwt = getJwtFromHeader(request);
            } else {
                jwt = cookieUtils.getCookieAccessToken(request); // получаем jwt из кука access_token
            }

            if (jwt != null) {
                if (jwtUtils.validate(jwt)) {
                    UserEntity user = jwtUtils.getUser(jwt);

                    UserDetailsImpl userDetails = new UserDetailsImpl(user);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new JwtCommonException("jwt validate exception");
                }
            } else {
                throw new AuthenticationCredentialsNotFoundException("token not found");
            }

        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER_PREFIX)) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
