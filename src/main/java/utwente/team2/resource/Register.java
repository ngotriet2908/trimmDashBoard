package utwente.team2.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import utwente.team2.dao.UserDao;
import utwente.team2.mail.EmailHtmlTemplate;
import utwente.team2.mail.MailAPI;
import utwente.team2.model.AvailabilityCheck;
import utwente.team2.settings.ApplicationSettings;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Path("/register")
public class Register {


    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream showLoginPage() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("../../html/register.html");

        return inputStream;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void register(@FormParam("username") String username, @FormParam("password") String password,
                         @FormParam("first_name") String firstName, @FormParam("last_name") String lastName,
                         @FormParam("email") String email, @Context HttpServletResponse servletResponse,
                         @Context HttpServletRequest servletRequest) throws IOException, MessagingException {

        System.out.println(username.matches("[a-zA-Z0-9_]{2,20}"));
        System.out.println(firstName.matches("[a-zA-Z\\-\\s]+"));
        System.out.println(email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"));
        System.out.println(password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}"));
        System.out.println(UserDao.instance.getUser(username, "", false) == null);
        System.out.println(!UserDao.instance.emailExist(email));



        // verifying/sanitizing input
        // check if the user already exists
        if (username.matches("[a-zA-Z0-9_]{2,20}") &&
                firstName.matches("[a-zA-Z\\-\\s]+") &&
                lastName.matches("[a-zA-Z\\-\\s]+") &&
                email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])") &&
                password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}") &&
                UserDao.instance.getUser(username, "", false) == null && !UserDao.instance.emailExist(email)) {

            if (UserDao.instance.register(username, firstName, lastName, email, password)) {
                ZoneId zoneId = ZoneId.systemDefault();

                Map<String, Object> claims = new HashMap<>();
                claims.put("iss", "runner");
                claims.put("sub", username);
                claims.put("exp", String.valueOf(LocalDateTime.now().plusMinutes(1440).atZone(zoneId).toEpochSecond()));
                claims.put("purpose", "activate");

                String token = Jwts.builder().setClaims(claims).signWith(ApplicationSettings.APP_KEY).compact();

                URL serverUrl = new URL(servletRequest.getRequestURL().toString());
                String callbackDomain = serverUrl.getProtocol() + "://" + serverUrl.getHost() + ":" + serverUrl.getPort();
                System.out.println("Server's domain: " + callbackDomain);

                MailAPI.generateAndSendEmail(EmailHtmlTemplate.createEmailHtml(username, token,
                        "You're receiving this email because you register an account on Runner.",
                        "ACTIVATE YOUR ACCOUNT",
                        callbackDomain + "/runner/register/activate?token=", callbackDomain),
                        "Activate your account", email);

                servletResponse.sendRedirect("/");
            } else {
                // cannot register the user
                System.out.println("400: Invalid data supplied. 1");
                servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid data supplied.");
            }
        } else {
            // if some of the checks fails, respond with failure
            System.out.println("400: Invalid data supplied. 2");
            servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid data supplied.");
        }
    }

    @Path("/username")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AvailabilityCheck checkUsernameAvailability(@QueryParam("username") String usernameToCheck, @Context HttpServletResponse servletResponse) {
        if (usernameToCheck == null) {
            return null;
        }

        return new AvailabilityCheck(UserDao.instance.getUser(usernameToCheck, "", false) != null);
    }

    @Path("/email")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AvailabilityCheck checkEmailAvailability(@QueryParam("email") String emailToCheck, @Context HttpServletResponse servletResponse) {
        if (emailToCheck == null) {
            return null;
        }

        return new AvailabilityCheck(UserDao.instance.emailExist(emailToCheck));
    }

    @Path("/activate")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public void activate(@QueryParam("token") String token, @Context HttpServletResponse servletResponse) throws IOException {
        Jws<Claims> jws = Jwts.parser().require("purpose", "activate")
                .setSigningKey(ApplicationSettings.APP_KEY).parseClaimsJws(token);
        System.out.println("Account activation JWT is valid.");
        String username = Login.getTokenClaims(token).getBody().getSubject();

        UserDao.instance.activateAccount(username);
        servletResponse.sendRedirect("/runner/login?message=activate_success");
    }
}
