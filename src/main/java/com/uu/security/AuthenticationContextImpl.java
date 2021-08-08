package com.uu.security;

import com.uu.orm.panache.User;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AuthenticationContextImpl implements AuthenticationContext {
    private User user;

    @Override
    public User getCurrentUser() {
        return null;
    }

    public void setCurrentUser(User user) {
        this.user = user;
    }
}
