package utwente.team2.resource;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import utwente.team2.dao.UserDao;
import utwente.team2.mail.EmailHtmlTemplate;
import utwente.team2.mail.MailAPI;
import utwente.team2.model.User;
import utwente.team2.settings.ApplicationSettings;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Path("/password")
public class PasswordReset {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @Context
    SecurityContext securityContext;

    @Path("/reset")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream showResetRequestPage() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("../../html/password_reset_request.html");
        return inputStream;
    }

    @Path("/reset")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void sendResetToken(@FormParam("username") String usernameOrEmail, @Context HttpServletResponse servletResponse,
                               @Context HttpServletRequest servletRequest) throws MessagingException, IOException, NoSuchMethodException, NoSuchMethodError {

        String username = null;
        User user;
        if (usernameOrEmail.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            username = UserDao.instance.getUserDetailsWithEmail(usernameOrEmail);
            user = UserDao.instance.getUserDetails(username);
        } else {
             user = UserDao.instance.getUserDetails(usernameOrEmail);
             if (user != null) {
                 username = user.getUsername();
             }
        }

        if (username == null || !username.matches("[a-zA-Z_]{2,}")) {
            servletResponse.sendRedirect("/");
            return;
        }


        if (user != null) {
            ZoneId zoneId = ZoneId.systemDefault();

            Map<String, Object> claims = new HashMap<>();
            claims.put("iss", "runner");
            claims.put("sub", username);
            claims.put("exp", String.valueOf(LocalDateTime.now().plusMinutes(15).atZone(zoneId).toEpochSecond()));
            claims.put("purpose", "password_reset");
            claims.put("key", (UserDao.instance.getUsersPassword(username)).substring(0, 5));

            String token = Jwts.builder().setClaims(claims).signWith(ApplicationSettings.APP_KEY).compact();

            URL serverUrl = new URL(servletRequest.getRequestURL().toString());
            String callbackDomain = serverUrl.getProtocol() + "://" + serverUrl.getHost() + ":" + serverUrl.getPort();
            System.out.println("Server's domain: " + callbackDomain);

            MailAPI.generateAndSendEmail(EmailHtmlTemplate.createEmailHtml(username, token,
                    "You're receiving this email because you requested a password reset for your user account on Runner. If you didn't request a password change, you can just ignore this message.",
                    "RESET YOUR PASSWORD",
                    callbackDomain + "/runner/password/reset/enter?token=", callbackDomain), "Reset your password - Runner", user.getEmail());
        }

        servletResponse.sendRedirect("/");
    }


    @Path("/reset/enter")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream showResetEnterPage(@QueryParam("token") String token,
                                          @Context HttpServletResponse servletResponse) throws IOException {

        try {
            if (token == null || token.equals("")) {
                servletResponse.sendError(401);
                return null;
            }

            String username = Login.getTokenClaims(token).getBody().getSubject();

            if (verifyResetJwt(token, username)) {
                // success
                ClassLoader classLoader = getClass().getClassLoader();
                return classLoader.getResourceAsStream("../../html/password_reset_enter.html");
            } else {
                servletResponse.sendError(404, "Invalid token.");
                return null;
            }
        } catch (JwtException e) {
            System.out.println("JWT exception.");
            servletResponse.setStatus(404);
            servletResponse.sendRedirect("/runner/login/?error=reset_token_invalid");
            return null;
        }
    }


    @Path("/reset/enter")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void resetEnter(@FormParam("token") String token, @FormParam("password") String password,
                           @Context HttpServletResponse servletResponse,
                           @Context HttpServletRequest servletRequest) throws IOException {
        try {

            if (token == null || token.equals("")) {
                servletResponse.sendError(401);
                return;
            }

            if (!password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}")) {
                servletResponse.sendError(400);
                return;
            }

            String username = Login.getTokenClaims(token).getBody().getSubject();

            if (!UserDao.instance.isActivated(username)) {
                System.out.println("Account is not activated");
                servletResponse.sendError(402, "account is not activated");
                return;
            }

            if (verifyResetJwt(token, username)) {
                // success
                UserDao.instance.updatePassword(username, password);
                servletResponse.sendRedirect("/runner/login?message=reset_success");
            } else {
                servletResponse.sendError(404);
            }
        } catch (JwtException e) {
            System.out.println("JWT exception.");
            servletResponse.sendError(404);
        }
    }


    public synchronized boolean verifyResetJwt(String token, String username) throws JwtException {
        String passwordHash = UserDao.instance.getUsersPassword(username);

        if (passwordHash != null) {
            Jws<Claims> jws = Jwts.parser().require("purpose", "password_reset").require("key", passwordHash.substring(0, 5))
                    .setSigningKey(ApplicationSettings.APP_KEY).parseClaimsJws(token);
            System.out.println("Password reset JWT is valid.");
            return true;
        } else {
            System.out.println("No user/password combination available. The token cannot be validated.");
            return false;
        }
    }
}
