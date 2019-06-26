package utwente.team2.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import utwente.team2.dao.UserDao;
import utwente.team2.resource.Login;
import utwente.team2.settings.ApplicationSettings;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    HttpServletRequest servletRequest;

    @Context
    HttpServletResponse servletResponse;

    @Context
    HttpHeaders headers;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        System.out.println("=================");
        System.out.println("Filter started...");
        System.out.println("Resource requested: " + requestContext.getUriInfo().getPath());

        Cookie jwsCookie = headers.getCookies().get("token");

        if (jwsCookie == null) {
            System.out.println("No cookie with token provided. Redirecting to login.");
            System.out.println("=================");
            forwardUnauthorized("not_authorized");
            abortWithUnauthorized(requestContext);
            return;
        }

        String jws = jwsCookie.getValue();

        try {
            // Validate the token
            validateToken(jws);

            final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext() {

                @Override
                public Principal getUserPrincipal() {
                    return () -> Login.getTokenClaims(Login.getCookie(servletRequest, "token").getValue()).getBody().getSubject();
                }

                @Override
                public boolean isUserInRole(String role) {
                    return true;
                }

                @Override
                public boolean isSecure() {
                    return currentSecurityContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return null;
                }
            });

            System.out.println("Filter passed.");
            System.out.println("=================");

        } catch (JwtException e) {

            if (e.getMessage().equals("password token is wrong!")) {
                forwardUnauthorized("password_change");
                abortWithUnauthorized(requestContext);
                return;
            }

            System.out.println("Filter blocked: token is invalid or expired.");
            System.out.println("=================");
            forwardUnauthorized("token_expired");
            abortWithUnauthorized(requestContext);
            return;
        }
    }

    private void validateToken(String token) throws JwtException {
        Jws<Claims> jws = Jwts.parser().setSigningKey(ApplicationSettings.APP_KEY).parseClaimsJws(token);
        if (!UserDao.instance.getUsersPassword(jws.getBody().getSubject()).substring(0,5)
                .equals(jws.getBody().get("key"))) {
            throw new JwtException("password token is wrong!");
        }
    }

    private void forwardUnauthorized(String error) {
        try {
            servletResponse.setStatus(401);
            servletResponse.sendRedirect("/runner/login/?error=" + error);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
    }
}