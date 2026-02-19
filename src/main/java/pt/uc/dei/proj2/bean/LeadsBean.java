package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.uc.dei.proj2.pojo.LeadsPojo;
import pt.uc.dei.proj2.pojo.UserPojo;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class LeadsBean implements Serializable {

    @Inject
    private UserBean userBean;

    /**
     * Lista todas as leads de um utilizador específico.
     */
    public List<LeadsPojo> getLeads(String username) {

        UserPojo user = userBean.getUserByUsername(username);

        if (user != null && user.getLeads() != null) {
            return user.getLeads();
        }

        return List.of(); // evita NullPointerException
    }

    /**
     * Adiciona uma nova lead ao utilizador.
     */
    public boolean addLead(String username, LeadsPojo newLead) {

        UserPojo user = userBean.getUserByUsername(username);

        if (user != null && user.getLeads() != null) {

            // Gerar ID automaticamente
            newLead.setId(generateId(user));

            // Definir data de criação
            newLead.setDate(Instant.now().toString());

            user.getLeads().add(newLead);

            userBean.save(); // Persistir alteração global

            return true;
        }

        return false;
    }

    /**
     * Edita uma lead existente.
     */
    public boolean editLead(String username, LeadsPojo editedLead) {

        UserPojo user = userBean.getUserByUsername(username);

        if (user != null && user.getLeads() != null) {

            for (int i = 0; i < user.getLeads().size(); i++) {

                if (user.getLeads().get(i).getId() == editedLead.getId()) {

                    user.getLeads().set(i, editedLead);

                    userBean.save();

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Remove uma lead pelo ID.
     */
    public boolean removeLead(String username, int leadId) {

        UserPojo user = userBean.getUserByUsername(username);

        if (user != null && user.getLeads() != null) {

            boolean removed = user.getLeads()
                    .removeIf(lead -> lead.getId() == leadId);

            if (removed) {
                userBean.save();
                return true;
            }
        }

        return false;
    }

    /**
     * Gera o próximo ID disponível para o utilizador.
     */
    private int generateId(UserPojo user) {

        return user.getLeads().stream()
                .mapToInt(LeadsPojo::getId)
                .max()
                .orElse(0) + 1;
    }
}
