package com.uu.security;

import com.uu.user.User;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AuthService {
    @Inject
    PasswordEncryptor passwordEncryptor;

    public Uni<User> verify(String username, String password) {
        return User.find("username", username).firstResult().map(user ->
                passwordEncryptor.checkPassword(password, ((User) user).getPassword()) ? (User) user : null
        );
    }
}
