package utwente.team2.resource;

import utwente.team2.dao.UserDao;
import utwente.team2.model.Username;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;

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


    // registers the user and logs in
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void register(@FormParam("username") String username, @FormParam("password") String password,
                         @FormParam("first_name") String firstName, @FormParam("last_name") String lastName,
                         @FormParam("email") String email, @Context HttpServletResponse servletResponse,
                      @Context HttpServletRequest servletRequest) throws IOException {

        // verification/sanitizing input
        // check if the user already exists
        if (username.matches("[a-zA-Z_]{2,}") &&
                firstName.matches("[a-zA-Z-]+") &&
                lastName.matches("[a-zA-Z-]+") &&
//                email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])") &&
                email.matches("\"^([\\p{L}-_\\.]+){1,64}@([\\p{L}-_\\.]+){2,255}.[a-z]{2,}$\"") &&
                password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}") &&
                UserDao.instance.getUser(username, "", false) == null) {

            if (UserDao.instance.register(username, firstName, lastName, email, password)) {

                servletResponse.sendRedirect("/");

                // show message "registered!" in client? TODO
            } else {
                // cannot register the user
                System.out.println("400: Invalid data supplied.");
                servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid data supplied.");
            }
        } else {
            // if some of the checks fails, respond with failure
            System.out.println("400: Invalid data supplied.");
            servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid data supplied.");
        }
    }

    @Path("/username")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Username checkUsernameAvailability(@QueryParam("username") String usernameToCheck, @Context HttpServletResponse servletResponse) {
        if (usernameToCheck == null) {
            return null; // TODO
        }

        Username username = new Username(usernameToCheck);
        username.setExists(UserDao.instance.getUser(username.getUsername(), "", false) != null);

        return username;
    }
}
