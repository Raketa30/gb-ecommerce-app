package ru.geekbrains.backend.security.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.geekbrains.backend.security.domain.entity.UserEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Component
@Log
public class JwtUtils {
    private static final String CLAIM_USER_KEY = "user";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access_token-expiration}")
    private int accessTokenExpiration;

    @Value("${jwt.reset_token-expiration}")
    private int resetTokenExpiration;

    public String createAccessToken(UserEntity user) {
        user.setPassword(null);
        return createToken(user, accessTokenExpiration);
    }

    public String createResetToken(UserEntity user) {
        return createToken(user, resetTokenExpiration);
    }

    private String createToken(UserEntity user, int time) {
        Date currentDate = new Date();

        Map<String, Object> claims = new HashMap();
        claims.put(CLAIM_USER_KEY, user);
        claims.put(Claims.SUBJECT, user.getId());

        return Jwts.builder()
//                .setSubject(user.getId().toString())
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + time))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    //  Сервер проверяет своим ключом JWT.
    public boolean validate(String jwt) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException e) {
            log.log(Level.SEVERE, "Invalid JWT token: ", jwt);
        } catch (ExpiredJwtException e) {
            log.log(Level.SEVERE, "JWT token is expired: ", jwt);
        } catch (UnsupportedJwtException e) {
            log.log(Level.SEVERE, "JWT token is unsupported: ", jwt);
        } catch (IllegalArgumentException e) {
            log.log(Level.SEVERE, "JWT claims string is empty: ", jwt);
        }
        return false;
    }

    public UserEntity getUser(String jwt) {
        Map map = (Map) Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody()
                .get(CLAIM_USER_KEY); // CLAIM_USER_KEY здесь - это поле из токена

        ObjectMapper mapper = new ObjectMapper();

        return mapper.convertValue(map, UserEntity.class);
    }
}
