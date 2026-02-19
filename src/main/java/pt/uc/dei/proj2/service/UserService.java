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

    @GET
    @Path("/{username}/profile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfile(
            @PathParam("username") String pathUser,
            @HeaderParam("username") String authUser,
            @HeaderParam("password") String authPass) {

        // 1. Validação de segurança
        if (authUser == null || authPass == null ||
                !userBean.login(authUser, authPass)) {

            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Acesso não autorizado\"}")
                    .build();
        }

        // 2. Garantir que só pode ver o próprio perfil
        if (!authUser.equals(pathUser)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\":\"Não pode aceder a outro perfil\"}")
                    .build();
        }

        // 3. Buscar utilizador
        var user = userBean.getUserByUsername(pathUser);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Utilizador não encontrado\"}")
                    .build();
        }

        return Response.ok(user).build();
    }

    @PUT
    @Path("/{username}/profile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(
            @PathParam("username") String pathUser,
            @HeaderParam("username") String authUser,
            @HeaderParam("password") String authPass,
            UserPojo updatedData) {

        // 1. Segurança
        if (authUser == null || authPass == null ||
                !userBean.login(authUser, authPass)) {

            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Acesso não autorizado\"}")
                    .build();
        }

        if (!authUser.equals(pathUser)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\":\"Não pode editar outro perfil\"}")
                    .build();
        }

        // 2. Buscar utilizador
        UserPojo user = userBean.getUserByUsername(pathUser);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Utilizador não encontrado\"}")
                    .build();
        }

        // 3. Atualizar apenas campos permitidos (NUNCA username)
        user.setPassword(updatedData.getPassword());
        user.setEmail(updatedData.getEmail());
        user.setFirstName(updatedData.getFirstName());
        user.setLastName(updatedData.getLastName());
        user.setCellphone(updatedData.getCellphone());
        user.setPhoto(updatedData.getPhoto());

        // 4. Persistir
        userBean.save();

        return Response.ok("{\"message\":\"Perfil atualizado com sucesso\"}").build();
    }
}




