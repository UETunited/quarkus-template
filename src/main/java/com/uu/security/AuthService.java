package com.uu.security;

import com.uu.user.User;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class AuthService {
    @Inject
    PasswordEncryptor passwordEncryptor;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public Uni<User> verify(String username, String password) {
        return User.find("username", username).firstResult()
                .onItem().ifNotNull().transform(user ->
                        passwordEncryptor.checkPassword(password, ((User) user).getPassword()) ? (User) user : null
                )
                .onItem().ifNull().fail();
    }

    public Uni<LoginResponse> login(LoginRequest loginRequest) {
        return verify(loginRequest.getUsername(), loginRequest.getPassword())
                .onItem().transform(this::createToken)
                .onFailure().transform(failure -> new UnauthorizedException(failure.getCause()));
    }

    public LoginResponse createToken(User user) {
        String token = Jwt.issuer(issuer)
                .upn(user.getUsername())
                .groups(new HashSet<>(Arrays.asList("USER"))) // default role USER
                .claim("abc", "Def")
                .expiresIn(Duration.of(7, ChronoUnit.DAYS))
//                .claim(Claims.birthdate.name(), "2001-07-13")
                // TODO: add more claims add your app needed
                .sign();
        return new LoginResponse(token);
    }

    public Uni<Void> register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setFullName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncryptor.hashPassword(registerRequest.getPassword()));
        return Panache.<User>withTransaction(user::persist).onItem().transform(item -> null);
    }
}
