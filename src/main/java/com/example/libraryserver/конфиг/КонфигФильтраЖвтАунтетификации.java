package com.example.libraryserver.конфиг;

import com.example.libraryserver.services.ЖвтСервис;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class КонфигФильтраЖвтАунтетификации extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final ЖвтСервис жвтСервис;
    /**
     * Фильтрует каждый запрос, проверяет наличие JWT токена в заголовке и аутентифицирует пользователя.
     *
     * @param request запрос
     * @param response ответ
     * @param filterChain цепочка фильтров
     * @throws ServletException в случае ошибки сервлета
     * @throws IOException в случае ошибки ввода-вывода
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        userEmail = жвтСервис.вычленитьИмяПользователя(jwtToken); // извлекаем из JWT токена

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);// проверяем, что полученное мыло не null, и что пользователь не залогинен
            if(жвтСервис.валидныйЛиТокен(jwtToken, userDetails)){// проверяем валидность токена
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities() //
                );// создаем аутентификационный токен
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));;
                // устанавливаем детали аутентификации
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
