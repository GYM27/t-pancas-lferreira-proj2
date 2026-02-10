package pt.uc.dei.proj2.service;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import pt.uc.dei.proj2.bean.UserBean;

@Path("/user")
public class UserService {

    @Inject
    UserBean userbean;

    @Context
    private HttpServletRequest request;


}


