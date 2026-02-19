package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.uc.dei.proj2.dao.DatabaseDao;
import pt.uc.dei.proj2.pojo.ClientesPojo;
import pt.uc.dei.proj2.pojo.UserPojo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped //cria apenas uma instância daquela classe para toda a aplicação enquanto ela estiver a correr
public class ClientesBean implements Serializable {


    @Inject
    private DatabaseDao dao;
    @Inject
    private UserBean userBean;

    public boolean addClients(String username, ClientesPojo newClient) {

        // Obtém o utilizador do estado centralizado
        UserPojo user = userBean.getUserByUsername(username);

        if (user != null) {
            user.getClientes().add(newClient);
            userBean.save(); // Grava a alteração global
            return true;
        }
        return false;
    }

    /**
     * Edita os dados de um cliente existente na lista de um utilizador específico.
     * * @param username O nome do utilizador dono da lista de clientes.
     *
     * @param editClient O objeto contendo os novos dados e o ID do cliente a editar.
     * @return true se o cliente foi encontrado e atualizado com sucesso; false caso contrário.
     */
    public boolean editClient(String username, ClientesPojo editClient) {
        // 1. Obtém o utilizador diretamente do estado central em memória
        UserPojo user = userBean.getUserByUsername(username);

        // 2. Procura o utilizador correto na lista global
        if (user != null && user.getClientes() != null) {
            // 2. Procura o cliente pelo ID dentro da lista desse utilizador
            for (int i = 0; i < user.getClientes().size(); i++) {
                if (user.getClientes().get(i).getId() == editClient.getId()) {

                    // 3. Substitui o cliente antigo pelo objeto com os novos dados
                    user.getClientes().set(i, editClient);

                    // 4. Pede ao UserBean para persistir a alteração global via DAO
                    userBean.save();
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Remove um cliente da lista de um utilizador.
     * * @param username O nome do utilizador dono do cliente.
     *
     * @param clientId O identificador único do cliente a ser removido.
     * @return true se o cliente foi removido com sucesso; false se não for encontrado.
     */
    public boolean removeClient(String username, int clientId) {
        // 1. Obtém o utilizador diretamente do estado centralizado no UserBean
        UserPojo user = userBean.getUserByUsername(username);

        if (user != null && user.getClientes() != null) {
            // 2. Tenta remover o cliente cujo ID coincida com o fornecido na lista em memória
            boolean removed = user.getClientes().removeIf(client -> client.getId() == clientId);

            if (removed) {
                // 3. Se algo foi removido, utiliza o UserBean para persistir a alteração global
                userBean.save();
                return true;
            }
        }
        return false;
    }

    public List<ClientesPojo> getClients(String username) {
        UserPojo user = userBean.getUserByUsername(username);
        if (user != null) {
            return user.getClientes(); // Devolve a lista real guardada no ficheiro JSON
        }
        return new ArrayList<>();

    }
}



