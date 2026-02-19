package pt.uc.dei.proj2.service;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.uc.dei.proj2.bean.ClientesBean;
import pt.uc.dei.proj2.bean.UserBean;
import pt.uc.dei.proj2.pojo.ClientesPojo;

@Path("/users/{username}/clients")
public class ClientesService {

    @Inject
    private UserBean userBean;
    @Inject
    private ClientesBean clientesBean;

    // ESTE MÉTODO É FIXO E NÃO MUDA: Resolve os 401 e 403
    private Response validateSecurity(String pathUser, String authUser, String authPass) {
        // 1. Erro 401: Não enviou nada no Header
        if (authUser == null || authUser.trim().isEmpty() || authPass == null || authPass.trim().isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Faltam credenciais (401)\"}").build();
        }

        // 2. Erro 403: Credenciais erradas
        if (!userBean.login(authUser, authPass)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\":\"Login inválido (403)\"}").build();
        }

        // 3. Erro 403: Tentativa de mexer em dados de outro utilizador
        if (!authUser.equals(pathUser)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\":\"Não pode aceder a dados alheios (403)\"}").build();
        }

        return null; // Tudo OK!
    }

    @GET
    public Response getClients(@PathParam("username") String pathUser,
                               @HeaderParam("username") String authUser,
                               @HeaderParam("password") String authPass) {

        Response check = validateSecurity(pathUser, authUser, authPass);
        if (check != null) return check;

        return Response.ok(userBean.getUserByUsername(pathUser).getClientes()).build();
    }


    // EXEMPLO DE USO NO ADICIONAR
    @POST
    @Path("/addClient")
    public Response addClient(@PathParam("username") String pathUser,
                              @HeaderParam("username") String authUser,
                              @HeaderParam("password") String authPass,
                              ClientesPojo client) {

        Response check = validateSecurity(pathUser, authUser, authPass);
        if (check != null) return check; // Se houver erro, para aqui e devolve 401 ou 403

        // Se passar, executa a lógica
        if (clientesBean.addClients(pathUser, client)) {
            return Response.status(Response.Status.CREATED).entity("{\"msg\":\"Ok\"}").build();
        }
        return Response.status(500).build();
    }

    @PUT
    @Path("/editClient")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editClient(@PathParam("username") String pathUser,
                               @HeaderParam("username") String authUser,
                               @HeaderParam("password") String authPass,
                               ClientesPojo client) {

        // 1. Validação de Segurança (401 ou 403)
        Response check = validateSecurity(pathUser, authUser, authPass);
        if (check != null) return check;

        // 2. Lógica de Negócio
        if (clientesBean.editClient(pathUser, client)) {
            return Response.ok("{\"message\":\"Cliente atualizado com sucesso\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Cliente não encontrado para edição\"}").build();
        }
    }

    @DELETE
    @Path("/remove")
    public Response removeClient(@PathParam("username") String pathUser,
                                 @HeaderParam("username") String authUser,
                                 @HeaderParam("password") String authPass,
                                 @QueryParam("id") int clientId) {

        // 1. Validação de Segurança (401 ou 403)
        Response check = validateSecurity(pathUser, authUser, authPass);
        if (check != null) return check;

        // 2. Lógica de Negócio
        if (clientesBean.removeClient(pathUser, clientId)) {
            return Response.ok("{\"message\":\"Cliente removido com sucesso\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Não foi possível encontrar o cliente para remover\"}").build();
        }
    }

}