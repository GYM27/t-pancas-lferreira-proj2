package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.uc.dei.proj2.dao.DatabaseDao;
import pt.uc.dei.proj2.pojo.ClientesPojo;
import pt.uc.dei.proj2.pojo.DatabasePojo;
import pt.uc.dei.proj2.pojo.UserPojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped //cria apenas uma instância daquela classe para toda a aplicação enquanto ela estiver a correr
public class ClientesBean implements Serializable {


    @Inject
    private DatabaseDao databaseDao;
    @Inject
    private UserBean userBean;


    public boolean addClients(String username, ClientesPojo newClient) {
        // 1. Vai buscar a base de dados (ficheiro JSON)
        DatabasePojo db = databaseDao.loadDatabase();

        // 2. Procura o utilizador na lista
        UserPojo currentUser = null;
        for (UserPojo u : db.getUsers()) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                currentUser = u;
                break;
            }
        }

        if (currentUser == null) return false;

        // --- CORREÇÃO IMPORTANTE ---
        // Se o utilizador nunca adicionou um cliente, a lista pode estar nula.
        // Inicializamos a lista para evitar o NullPointerException (Erro 500).
        if (currentUser.getClients() == null) {
            currentUser.setClients(new ArrayList<>());
        }

        // 3. GERAÇÃO DO ID MANUAL (Sem Stream)
        int maiorId = 0;
        // Percorre todos os clientes existentes do utilizador para encontrar o ID mais alto
        for (ClientesPojo c : currentUser.getClients()) {
            if (c.getId() > maiorId) {
                maiorId = c.getId();
            }
        }

        // O novo ID será o maior encontrado + 1 para manter a sequência numérica
        newClient.setId(maiorId + 1);

        // 4. Adiciona à lista e guarda no ficheiro único JSON [cite: 237, 52]
        currentUser.getClients().add(newClient);

        // Persiste as alterações no ficheiro dataBase.json [cite: 8, 52]
        return databaseDao.saveDatabase(db);
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
        if (user != null && user.getClients() != null) {
            // 2. Procura o cliente pelo ID dentro da lista desse utilizador
            for (int i = 0; i < user.getClients().size(); i++) {
                if (user.getClients().get(i).getId() == editClient.getId()) {

                    // 3. Substitui o cliente antigo pelo objeto com os novos dados
                    user.getClients().set(i, editClient);

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

        if (user != null && user.getClients() != null) {
            // 2. Tenta remover o cliente cujo ID coincida com o fornecido na lista em memória
            boolean removed = user.getClients().removeIf(client -> client.getId() ==(clientId));

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
            return user.getClients(); // Devolve a lista real guardada no ficheiro JSON
        }
        return new ArrayList<>();

    }
}



