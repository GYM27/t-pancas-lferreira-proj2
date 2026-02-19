package pt.uc.dei.proj2.service;

import jakarta.ws.rs.core.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.uc.dei.proj2.bean.UserBean;
import pt.uc.dei.proj2.dto.LoginDto;
import pt.uc.dei.proj2.pojo.UserPojo;
import jakarta.ws.rs.*;


@Path("/users")
public class UserService {

    @Inject
    private UserBean userBean; // Isto liga as duas classes automaticamente

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserPojo newUser) {
        boolean success = userBean.register(newUser);
        if (success) {
            return Response.status(Response.Status.CREATED).entity("{\"message\":\"User registered\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Username already exists\"}").build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDto loginData) {
        System.out.println("Tentativa de login para: " + loginData.getUsername());
        boolean success = userBean.login(loginData.getUsername(), loginData.getPassword());
        if (success) {
            return Response.ok("{\"message\":\"Login successful\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("/getUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@QueryParam("username") String username) {
        UserPojo user = userBean.getUserByUsername(username);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"User not found\"}").build();
        }
    }



}




