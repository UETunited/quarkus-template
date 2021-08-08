package com.uu.security;

import com.uu.orm.panache.User;

public interface AuthenticationContext {
    User getCurrentUser();
}
