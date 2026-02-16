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
     * Adiciona um novo cliente à lista de um utilizador específico.
     * O acesso é validado através de credenciais passadas no URL e nos Headers.
     * * @param username nome de utilizador recebido no caminho do URL.
     *
     * @param password A palavra-passe do utilizador recebida no Header para autenticação simples.
     * @param client   O objeto contendo os dados do cliente enviado no corpo do pedido (JSON).
     * @return {@link Response} 201 (Created) em caso de sucesso,
     * 401 (Unauthorized) se as credenciais falharem,
     * ou 500 (Internal Server Error) se ocorrer um erro na gravação.
     */
    @POST
    @Path("/addClient")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addClient(@PathParam("username") String username,
                              @HeaderParam("password") String password,
                              ClientesPojo client) {

        // Criação de DTO temporário para validar o login através do UserBean
        LoginDto loginCheck = new LoginDto(username, password);

        // Verificação obrigatória de segurança baseada nos requisitos do projeto
        if (!userBean.login(loginCheck)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Acesso negado: Credenciais inválidas\"}")
                    .build();
        }

        // Delegação da lógica de negócio e persistência para o ClientesBean
        boolean success = clientesBean.addClients(username, client);

        if (success) {
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
     * * @param username O nome de utilizador extraído do URL.
     *
     * @param password A palavra-passe extraída do Header "password" para verificação.
     * @param editclient   O objeto JSON com os novos dados do cliente (deve incluir o ID).
     * @return {@link Response} 200 (OK) se editado, 401 (Unauthorized) se a senha falhar,
     * ou 404 (Not Found) se o cliente não existir.
     */
    @PUT
    @Path("/editClient")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editClient(@PathParam("username") String username,
                               @HeaderParam("password") String password,
                               ClientesPojo editclient) {

        // 1. Validação de segurança: Verifica se o utilizador tem permissão
        LoginDto loginCheck = new LoginDto(username, password);
        if (!userBean.login(loginCheck)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Acesso negado: Credenciais inválidas\"}")
                    .build();
        }

        // 2. Tentativa de edição: O Bean procura o cliente pelo ID e substitui os dados
        boolean success = clientesBean.editClient(username, editclient);

        if (success) {
            return Response.ok("{\"message\":\"Cliente atualizado\"}").build();
        } else {
            // Retorna 404 caso o utilizador seja válido mas o ID do cliente não exista
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Cliente não encontrado\"}")
                    .build();
        }
    }

    /**
     * Remove um cliente do sistema.
     * * @param username Nome do utilizador no URL.
     *
     * @param password Password no Header para autenticação.
     * @param clientId ID do cliente passado como Query Parameter.
     * @return Response 200 (OK) ou 401 (Unauthorized).
     */
    @DELETE
    @Path("/remove")
    public Response removeClient(@PathParam("username") String username,
                                 @HeaderParam("password") String password,
                                 @QueryParam("id") int clientId) {

        LoginDto loginCheck = new LoginDto(username, password);
        if (!userBean.login(loginCheck)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Acesso negado: Credenciais inválidas\"}")
                    .build();
        }

        if(clientesBean.removeClient(username,clientId)){
            return Response.ok("Cliente removido com sucesso").build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


}