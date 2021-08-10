package com.uu.security;

import com.uu.common.CommonResponse;
import com.uu.user.User;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.HashSet;


@Path("/auth")
@RequestScoped
@Slf4j
public class AuthController {

    @Inject
    JwtTokenProvider tokenProvider;

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @PermitAll
    public Uni<LoginResponse> authenticateUser(LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @POST
    @Path("/logout")
    @RolesAllowed({"USER", "ADMIN"})
    public CommonResponse logout(@Context SecurityContext ctx) {
        log.info("principal {}", ctx.getUserPrincipal());
        return CommonResponse.success(null);
    }

    @POST
    @Path("/register")
    @PermitAll
    public Uni<CommonResponse> register(RegisterRequest registerRequest) {
        return authService.register(registerRequest).onItem().transform(item -> CommonResponse.success(null));
    }
}
