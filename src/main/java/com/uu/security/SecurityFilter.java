package com.uu.security;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import java.io.IOException;

@PreMatching
public class SecurityFilter implements ContainerRequestFilter {
    private static final String AUTH_HEADER = "Authorization";

    @Inject
    AuthenticationContextImpl authCtx;

    @Inject
    JwtTokenProvider jwtTokenProvider;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authToken = requestContext.getHeaders().getFirst(AUTH_HEADER);
        // TODO: implement session mechanism or JWT parser and set current User to authCtx
        // after that, other beans of request scope can inject AuthenticationContextImpl to get current logged in user
        if (jwtTokenProvider.validateToken(authToken)) {

            authCtx.setCurrentUser(null);
        }
    }
}
