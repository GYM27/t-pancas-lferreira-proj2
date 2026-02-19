package pt.uc.dei.proj2.service;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pt.uc.dei.proj2.bean.LeadsBean;
import pt.uc.dei.proj2.bean.UserBean;
import pt.uc.dei.proj2.dto.LoginDto;
import pt.uc.dei.proj2.pojo.LeadsPojo;

import java.util.List;

@Path("/users/{username}/leads")  //define o endpoint base
@Consumes(MediaType.APPLICATION_JSON)  //diz que recebe o JSON
@Produces(MediaType.APPLICATION_JSON)  //diz que devolve JSON
public class LeadsService {

    @Inject
    private LeadsBean leadsBean;

    @Inject
    private UserBean userBean;


    // LeadsService.java

    private Response validateSecurity(String pathUser, String authUser, String authPass) {
        // 1. REQUISITO 401: Headers vazios (Unauthorized)
        if (authUser == null || authPass == null || authUser.isEmpty() || authPass.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Autenticação necessária (401)\"}").build();
        }

        // 2. REQUISITO 403: Credenciais inválidas (Forbidden)
        // Agora compatível com UserBean.login(String, String)
        if (!userBean.login(authUser, authPass)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\":\"Acesso proibido: Credenciais erradas (403)\"}").build();
        }

        // 3. REQUISITO 403: Verificação de Posse
        if (!authUser.equals(pathUser)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\":\"Acesso negado: Não é o dono destas Leads (403)\"}").build();
        }

        return null; // Sucesso
    }


    //Listar leads
    @GET
    public Response getLeads(
            @PathParam("username") String username,
            @HeaderParam("username") String authUser,
            @HeaderParam("password") String authPass) {

        Response error = validateSecurity(username, authUser, authPass);
        if (error != null) return error;

        List<LeadsPojo> leads = leadsBean.getLeads(username);
        return Response.ok(leads).build();
    }

    //Adicionar lead
    @POST
    public Response addLead(
            @PathParam("username") String username,
            @HeaderParam("username") String authUser,
            @HeaderParam("password") String authPass,
            LeadsPojo lead) {

        Response error = validateSecurity(username, authUser, authPass);
        if (error != null) return error;

        boolean success = leadsBean.addLead(username, lead);
        if (!success) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Erro ao adicionar lead\"}").build();
        }

        return Response.status(Response.Status.CREATED).build();
    }


    //Atualizar lead
    @PUT
    @Path("/{id}")
    public Response updateLead(
            @PathParam("username") String username,
            @PathParam("id") int id,
            @HeaderParam("username") String authUser,
            @HeaderParam("password") String authPass,
            LeadsPojo lead) {

        Response error = validateSecurity(username, authUser, authPass);
        if (error != null) return error;

        boolean success = leadsBean.updateLead(username, id, lead);
        if (!success) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().build();
    }


    //Remover leaad
    @DELETE
    @Path("/{id}")
    public Response removeLead(
            @PathParam("username") String username,
            @PathParam("id") int id,
            @HeaderParam("username") String authUser,
            @HeaderParam("password") String authPass) {

        Response error = validateSecurity(username, authUser, authPass);
        if (error != null) return error;

        boolean success = leadsBean.removeLead(username, id);
        if (!success) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().build();
    }
}
