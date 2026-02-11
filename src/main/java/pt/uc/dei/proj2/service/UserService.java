package pt.uc.dei.proj2.service;

import jakarta.ws.rs.core.*;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.uc.dei.proj2.bean.UserBean;
import pt.uc.dei.proj2.dto.UserDto;
import pt.uc.dei.proj2.pojo.LoginResponse;
import pt.uc.dei.proj2.pojo.UserPojo;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.uc.dei.proj2.bean.UserBean;
import pt.uc.dei.proj2.pojo.UserPojo;

import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UserService {

    @Inject
    private UserBean userBean; // Isto liga as duas classes automaticamente

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponse login(UserPojo user) {
        /*Map<String, Object> json = new HashMap<>();
        json.put("Hello", "World");
        return Response.ok(json).build();*/
        return new LoginResponse(
                true,
                "Login successful " + user.getEmail(),
                123L
        );
    }
}



