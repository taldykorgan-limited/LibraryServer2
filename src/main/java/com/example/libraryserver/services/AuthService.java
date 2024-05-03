package com.example.libraryserver.services;

import com.example.libraryserver.entities.Role;
import com.example.libraryserver.entities.UserEntity;
import com.example.libraryserver.repositories.UserRepository;
import com.example.libraryserver.requests.books.AuthenticationRequest;
import com.example.libraryserver.requests.books.RegisterRequest;
import com.example.libraryserver.responses.books.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Сервис для работы с аутентификацией пользователей.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Метод для аутентификации пользователя.
     *
     * @param request запрос на аутентификацию
     * @return ответ с JWT токеном
     */
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByLogin(request.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .build();
    }

    /**
     * Метод для регистрации нового пользователя.
     *
     * @param request запрос на регистрацию
     * @return ответ с JWT токеном
     */
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        Optional<UserEntity> userRepositoryByEmail = userRepository.findByLogin(request.getLogin());

        if (userRepositoryByEmail.isPresent()) {
            throw new RuntimeException("User with email " + request.getLogin() + " already exists");
        }

        var user = UserEntity.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Метод для создания администратора.
     *
     * @param request запрос на создание администратора
     * @return ответ с JWT токеном
     */
//    public AuthenticationResponse createAdmin (RegisterRequest request)  {
//        var user = UserEntity.builder()
//                .name(request.getName())
//                .surname(request.getSurname())
//                .email(request.getEmail())
//                .passwordus(passwordEncoder.encode(request.getPassword()))
//                .role(Role.ADMIN)
//                .build();
//        try {
//            var userDB = userRepository.findByEmail(request.getEmail()).orElseThrow();
//            if (user.getUsername().equals(userDB.getUsername()))
//                throw new UsernameNotFoundException("потом доделаю и не ебет");
//            // todo сделать нормально, а не хуйню с исключениями
//        }catch (Exception e){
//
//        }
//        userRepository.save(user);
//        var jwtToken = жвтСервис.сгенерироватьТокен(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }

    /**
     * Метод для изменения роли пользователя.
     *
     * @param request запрос на изменение роли
     * @return новая роль пользователя
     */
//    public String changeRole(ChangeRoleRequest request) {
//        var user = userRepository.findByLogin(request.getLogin()).orElseThrow(() -> new RuntimeException("User email not found " + request.getLogin()));
//        user.setRole(Role.ADMIN);
//        userRepository.save(user);
//        return user.getRole().toString();
//    }
}