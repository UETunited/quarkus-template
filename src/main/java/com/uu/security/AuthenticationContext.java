package com.uu.security;

import com.uu.user.User;

public interface AuthenticationContext {
    User getCurrentUser();
}
