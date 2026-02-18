package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.uc.dei.proj2.pojo.UserPojo;
import pt.uc.dei.proj2.pojo.LeadsPojo;

import java.io.Serializable;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class LeadsBean implements Serializable {

    @Inject
    private UserBean userBean;

    //Listar leads - recebe o username que vem do service
    public List<LeadsPojo> getLeads(String username) {

        //Vai buscar o utilizador ao UserBean
        UserPojo user = userBean.getUserByUsername(username);

        //Pode devolver lista vazia em vez de null, uma vez que evita o NullPointerException no Service
        if (user == null) {
            return List.of();
        }
        //devolve a lista de leads daquele utilizador
        return user.getLeads();
    }


    //Adicionar Lead - recebe username e objeto leaad, vindo do service
   public boolean addLead(String username, LeadsPojo lead) {

        //Vai buscar o utilizador
        UserPojo user = userBean.getUserByUsername(username);

        if (user == null) {
            return false;
        }

        //Gera o Id automaticamente. Cada utilizador tem os seus próprios IDs
        lead.setId(generateId(user));
        //Define a data de criação automaticamente
        lead.setDate(Instant.now().toString());

        //Adiciona a lead à lista do utilizador
        user.getLeads().add(lead);

        userBean.save(); //Pede ao UserBean para escrever users.json

        return true;
    }


    //Atualizar lead - recebe username, d do lead e ojeto com novos dados
    public boolean updateLead(String username, int id, LeadsPojo updatedLead) {

        //Vai biscar o utilizador
        UserPojo user = userBean.getUserByUsername(username);

        if (user == null) {
            return false;
        }

        //percorre a lista de leads daquele utilizador
        for (LeadsPojo lead : user.getLeads()) {

            //procura o lead correto
            if (lead.getId() == id) {

                //atualiza os campos
                lead.setTitle(updatedLead.getTitle());
                lead.setDescription(updatedLead.getDescription());
                lead.setState(updatedLead.getState());

                //persiste alterações no ficheiro JSON
                userBean.save();

                return true;
            }
        }

        return false;
    }


    //Remover lead - recebe username e id
    public boolean removeLead(String username, int id) {

        UserPojo user = userBean.getUserByUsername(username);

        if (user == null) {
            return false;
        }

        boolean removed = false;

        Iterator<LeadsPojo> iterator = user.getLeads().iterator();

        while (iterator.hasNext()) {
            LeadsPojo lead = iterator.next();

            if (lead.getId() == id) {
                iterator.remove();   // remove de forma segura
                removed = true;
                break;               // como os IDs são únicos, podemos parar
            }
        }

        if (removed) {
            userBean.save();
        }

        return removed;
    }

    //Gerar Id baseado nos leads daquele utilizador
    private int generateId(UserPojo user) {

        //vai buscar todos os Ids, encontra o maior, se não houver nenhum, começa em 0, soma +1 paara termos o próximo ID DISPONÍVEL
        return user.getLeads().stream()
                .mapToInt(LeadsPojo::getId)
                .max()
                .orElse(0) + 1;
    }
}

