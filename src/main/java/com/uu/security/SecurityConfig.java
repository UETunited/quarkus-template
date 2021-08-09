package com.uu.security;

import io.quarkus.arc.DefaultBean;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class SecurityConfig {
    @Produces
    @DefaultBean
    public PasswordEncryptor bCryptPasswordEncryptor() {
        return new PasswordEncryptor() {
            @Override
            public String hashPassword(String password) {
                return BCrypt.hashpw(password, BCrypt.gensalt(12));
            }

            @Override
            public boolean checkPassword(String plainTextPassword, String hashedPassword) {
                return BCrypt.checkpw(plainTextPassword, hashedPassword);
            }
        };
    }
}
