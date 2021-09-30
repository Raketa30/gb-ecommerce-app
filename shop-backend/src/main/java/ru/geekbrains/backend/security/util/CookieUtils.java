package ru.geekbrains.backend.security.util;

import lombok.extern.java.Log;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
@Log
public class CookieUtils {

    @Value("${cookie.jwt.name}")
    private String cookieJwtName;

    @Value("${server.domain}")
    private String cookieAccessTokenDomain;

    @Value("${cookie.jwt.max-age}")
    private int cookieAccessTokenDuration;

    //создаем кук
    public HttpCookie createJwtCookie(String jwt) {
        return ResponseCookie
                .from(cookieJwtName, jwt)
                .maxAge(cookieAccessTokenDuration)
                .sameSite(SameSiteCookies.STRICT.getValue())
                .httpOnly(true)
                .secure(true)
                .domain(cookieAccessTokenDomain)
                .path("/")
                .build();
    }

    //зануляем кук
    public HttpCookie deleteCookie() {
        return ResponseCookie
                .from(cookieJwtName, "null")
                .maxAge(0)
                .sameSite(SameSiteCookies.STRICT.getValue())
                .httpOnly(true)
                .secure(true)
                .domain(cookieAccessTokenDomain)
                .path("/")
                .build();
    }

    // получает значение кук access_token и возвращает его значение (JWT)
    public String getCookieAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieJwtName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
