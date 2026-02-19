package pt.uc.dei.proj2.service;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.uc.dei.proj2.bean.LeadsBean;
import pt.uc.dei.proj2.bean.UserBean;
import pt.uc.dei.proj2.pojo.LeadsPojo;

import java.util.List;

@Path("/users/{username}/leads")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LeadsService {

    @Inject
    private UserBean userBean;

    @Inject
    private LeadsBean leadsBean;


    //Método fixo de segurança
    private Response validateSecurity(String pathUser, String authUser, String authPass) {

        // 1 - 401 - Sem credenciais
        if (authUser == null || authUser.trim().isEmpty()
                || authPass == null || authPass.trim().isEmpty()) {

            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Faltam credenciais (401)\"}")
                    .build();
        }

        // 2 - 403 - Credenciais erradas
        if (!userBean.login(authUser, authPass)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\":\"Login inválido (403)\"}")
                    .build();
        }

        // 3 - 403 - Acesso a dados de outro utilizador
        if (!authUser.equals(pathUser)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\":\"Não pode aceder a dados alheios (403)\"}")
                    .build();
        }

        return null; // Tudo OK
    }


    //Get - Listar leads
    @GET
    public Response getLeads(@PathParam("username") String pathUser,
                             @HeaderParam("username") String authUser,
                             @HeaderParam("password") String authPass) {

        Response check = validateSecurity(pathUser, authUser, authPass);
        if (check != null) return check;

        List<LeadsPojo> leads = leadsBean.getLeads(pathUser);

        return Response.ok(leads).build();
    }


    //Post - Adicionar Lead
    @POST
    @Path("/addLead")
    public Response addLead(@PathParam("username") String pathUser,
                            @HeaderParam("username") String authUser,
                            @HeaderParam("password") String authPass,
                            LeadsPojo lead) {

        Response check = validateSecurity(pathUser, authUser, authPass);
        if (check != null) return check;

        if (leadsBean.addLead(pathUser, lead)) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"msg\":\"Lead criada com sucesso\"}")
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }


    //Put - Editar Lead
    @PUT
    @Path("/editLead")
    public Response editLead(@PathParam("username") String pathUser,
                             @HeaderParam("username") String authUser,
                             @HeaderParam("password") String authPass,
                             LeadsPojo lead) {

        Response check = validateSecurity(pathUser, authUser, authPass);
        if (check != null) return check;

        if (leadsBean.editLead(pathUser, lead)) {
            return Response.ok("{\"message\":\"Lead atualizada com sucesso\"}")
                    .build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Lead não encontrada para edição\"}")
                .build();
    }


    //Delete - Remover Lead
    @DELETE
    @Path("/remove")
    public Response removeLead(@PathParam("username") String pathUser,
                               @HeaderParam("username") String authUser,
                               @HeaderParam("password") String authPass,
                               @QueryParam("id") int leadId) {

        Response check = validateSecurity(pathUser, authUser, authPass);
        if (check != null) return check;

        if (leadsBean.removeLead(pathUser, leadId)) {
            return Response.ok("{\"message\":\"Lead removida com sucesso\"}")
                    .build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Lead não encontrada para remoção\"}")
                .build();
    }
}
