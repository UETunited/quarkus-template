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

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String sessionId = requestContext.getHeaders().getFirst(AUTH_HEADER);
        // TODO: implement session mechanism or JWT parser and set current User to authCtx

        authCtx.setCurrentUser(null);
    }
}
