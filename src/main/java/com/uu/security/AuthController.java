package com.uu.security;

import com.uu.common.CommonResponse;
import com.uu.user.User;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


@Path("/auth")
public class AuthController {

    @Inject
    private JwtTokenProvider tokenProvider;

    @POST
    @Path("/login")
    public LoginResponse authenticateUser(LoginRequest loginRequest) {

        User user = User.find()

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    @POST
    @Path("/register")
    public CommonResponse register(RegisterRequest registerRequest) {
        User registered = userService.createUser(registerRequest);
        return CommonResponse.success(null);
    }
}
