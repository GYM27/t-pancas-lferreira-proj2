package pt.uc.dei.proj2.service;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.uc.dei.proj2.bean.ClientesBean;
import pt.uc.dei.proj2.bean.UserBean;
import pt.uc.dei.proj2.dto.LoginDto;
import pt.uc.dei.proj2.pojo.ClientesPojo;

/**
 * Serviço REST para gestão de operações relacionadas com clientes de um utilizador.
 * Disponibiliza endpoints para adicionar e manipular dados de clientes.
 */
@Path("/users/{username}/clients")
public class ClientesService {

    @Inject
    private UserBean userBean;

    @Inject
    private ClientesBean clientesBean;

    /**
     * Método auxiliar para uniformizar a autenticação com os restantes serviços.
     */
    private boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        // Utiliza o componente de utilizadores para validar o acesso
        return userBean.login(new LoginDto(username, password));
    }

    @POST
    @Path("/addClient")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addClient(@PathParam("username") String username,
                              @HeaderParam("password") String password,
                              ClientesPojo client) {

        // 1. Validação de segurança centralizada
        if (!authenticate(username, password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Acesso negado: Credenciais inválidas\"}")
                    .build();
        }

        // 2. Execução da lógica de negócio através do bean de clientes
        if (clientesBean.addClients(username, client)) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\":\"Cliente adicionado com sucesso\"}")
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Erro ao guardar o cliente\"}")
                    .build();
        }
    }

    /**
     * Edita os dados de um cliente existente para um utilizador específico.
     */
    @PUT
    @Path("/editClient")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editClient(@PathParam("username") String username,
                               @HeaderParam("password") String password,
                               ClientesPojo editclient) {

        // 1. Validação de segurança utilizando o método centralizado
        if (!authenticate(username, password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Acesso negado: Credenciais inválidas\"}")
                    .build();
        }

        // 2. Tentativa de edição através do Bean
        if (clientesBean.editClient(username, editclient)) {
            return Response.ok("{\"message\":\"Cliente atualizado\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Cliente não encontrado\"}")
                    .build();
        }
    }

    /**
     * Remove um cliente do sistema.
     */
    @DELETE
    @Path("/remove")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeClient(@PathParam("username") String username,
                                 @HeaderParam("password") String password,
                                 @QueryParam("id") int clientId) {

        // 1. Validação de segurança utilizando o método centralizado
        if (!authenticate(username, password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Acesso negado: Credenciais inválidas\"}")
                    .build();
        }

        // 2. Remoção através do Bean
        if (clientesBean.removeClient(username, clientId)) {
            return Response.ok("{\"message\":\"Cliente removido com sucesso\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Cliente não encontrado\"}")
                    .build();
        }
    }
}