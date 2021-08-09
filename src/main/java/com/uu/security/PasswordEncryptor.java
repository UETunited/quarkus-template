package com.uu.security;

public interface PasswordEncryptor {
    String hashPassword(String password);
    boolean checkPassword(String plainTextPassword, String hashedPassword);
}
