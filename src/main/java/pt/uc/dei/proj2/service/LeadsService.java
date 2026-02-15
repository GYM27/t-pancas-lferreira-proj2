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


    //Autenticação auxiliar. Método interno para repetir código
    private boolean authenticate(String authUser, String authPass) {

        //Se o header não vier, não autentica
        if (authUser == null || authPass == null) {
            return false;
        }
        //valida username + password no JSON
        return userBean.login(new LoginDto(authUser, authPass));
    }


    //Listar leads
    @GET
    public Response getLeads(
            @PathParam("username") String username,
            @HeaderParam("username") String authUser,
            @HeaderParam("password") String authPass) {

        //Segurança dupla: valida credenciais e garante que só acedemos às próprias leads
        if (!authenticate(authUser, authPass) || !authUser.equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        //Vai ao Bean buscar os dados e devolve 200 ok + JSON
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

        //Segurança dupla: valida credenciais e garante que só acedemos às próprias leads
        if (!authenticate(authUser, authPass) || !authUser.equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        //Chama a lógica no Bean
        boolean success = leadsBean.addLead(username, lead);

        if (!success) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //201 created para a criação
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

        //Segurança dupla: valida credenciais e garante que só acedemos às próprias leads
        if (!authenticate(authUser, authPass) || !authUser.equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        //Atualiza no Bean
        boolean success = leadsBean.updateLead(username, id, lead);

        if (!success) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        //200 ok, atualização concluída
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

        //Segurança dupla: valida credenciais e garante que só acedemos às próprias leads
        if (!authenticate(authUser, authPass) || !authUser.equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        //Remove no Bean
        boolean success = leadsBean.removeLead(username, id);

        if (!success) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        //200 ok - removido com sucesso
        return Response.ok().build();
    }
}
