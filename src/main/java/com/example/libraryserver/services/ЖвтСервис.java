package com.example.libraryserver.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class ЖвтСервис {
    private static final String СЕКРЕТНЫЙ_КЛЮЧ = ("9c56bbb2442aa20f7d48ce5ba13b75c38000266334fb008387322a8a8ff24944").toUpperCase();

    /**
     * Извлекает имя пользователя из JWT токена.
     *
     * @param jwtToken JWT токен
     * @return имя пользователя
     */
    public String вычленитьИмяПользователя(String jwtToken) {
        return вычленитьУтверждение(jwtToken, Claims::getSubject);
    }

    /**
     * Извлекает определенное утверждение из JWT токена.
     *
     * @param jwtToken JWT токен
     * @param claimsResolver функция для извлечения утверждения
     * @return утверждение
     */
    public <T> T вычленитьУтверждение(String jwtToken, Function<Claims, T> claimsResolver){
        final Claims claims = вычленитьВсеУтверждения(jwtToken);
        return claimsResolver.apply(claims);
    }

    /**
     * Генерирует JWT токен для пользователя.
     *
     * @param userDetails детали пользователя
     * @return JWT токен
     */
    public String сгенерироватьТокен(UserDetails userDetails){
        return сгенерироватьТокен(new HashMap<>(), userDetails);
    }

    /**
     * Генерирует JWT токен с утверждениями для пользователя.
     *
     * @param claims утверждения
     * @param userDetails детали пользователя
     * @return JWT токен
     */
    public String сгенерироватьТокен(Map<String, Objects> claims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)))
                .signWith(получитьКлючПодписи(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Проверяет валидность JWT токена для пользователя.
     *
     * @param jwtToken JWT токен
     * @param userDetails детали пользователя
     * @return true, если токен валиден, иначе false
     */
    public boolean валидныйЛиТокен(String jwtToken, UserDetails userDetails){
        final String username = вычленитьИмяПользователя(jwtToken);
        return username.equals(userDetails.getUsername()) && !истекЛиТокен(jwtToken);
    }

    /**
     * Проверяет, истекло ли время действия JWT токена.
     *
     * @param jwtToken JWT токен
     * @return true, если время действия истекло, иначе false
     */
    private boolean истекЛиТокен(String jwtToken) {
        return вычленитьВремяИстечения(jwtToken).before(new Date());
    }

    /**
     * Извлекает время истечения JWT токена.
     *
     * @param jwtToken JWT токен
     * @return время истечения
     */
    private Date вычленитьВремяИстечения(String jwtToken) {
        return вычленитьУтверждение(jwtToken, Claims::getExpiration);
    }

    /**
     * Извлекает все утверждения из JWT токена.
     *
     * @param jwtToken JWT токен
     * @return утверждения
     */
    private Claims вычленитьВсеУтверждения(String jwtToken){
        return Jwts
                .parserBuilder()
                .setSigningKey(получитьКлючПодписи())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * Возвращает ключ для подписи JWT токена.
     *
     * @return ключ
     */
    private Key получитьКлючПодписи() {
        byte[] keyBytes = Decoders.BASE64.decode(СЕКРЕТНЫЙ_КЛЮЧ);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
