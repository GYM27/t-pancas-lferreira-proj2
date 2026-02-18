package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.bind.JsonbBuilder;
import pt.uc.dei.proj2.dao.DatabaseDao;
import pt.uc.dei.proj2.dto.ClientesDto;
import pt.uc.dei.proj2.pojo.ClientesPojo;
import pt.uc.dei.proj2.pojo.DatabasePojo;
import pt.uc.dei.proj2.pojo.UserPojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;

@ApplicationScoped //cria apenas uma instância daquela classe para toda a aplicação enquanto ela estiver a correr
public class ClientesBean implements Serializable {


    @Inject
    private DatabaseDao dao;


    public boolean addClients(String username, ClientesPojo newClient) {

        DatabasePojo database = dao.loadDatabase();

        for (UserPojo user : database.getUsers()) {
            if (user.getUsername().equals(username)) {
                user.getClientes().add(newClient);

                dao.saveDatabase(database);
                return true;
            }
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
        // 1. Carrega o estado atual da base de dados através do DAO
        DatabasePojo database = dao.loadDatabase();

        // 2. Procura o utilizador correto na lista global
        for (UserPojo user : database.getUsers()) {
            if (user.getUsername().equals(username)) {
                // 3. Procura o cliente pelo ID dentro da lista desse utilizador
                for (int i = 0; i < user.getClientes().size(); i++) {
                    if (user.getClientes().get(i).getId() == editClient.getId()) {
                        // 4. Substitui o cliente antigo pelo objeto atualizado
                        user.getClientes().set(i, editClient);

                        // 5. Guarda as alterações no ficheiro JSON
                        dao.saveDatabase(database);
                        return true;
                    }
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
        // 1. Carrega os dados do ficheiro
        DatabasePojo database = dao.loadDatabase();

        for (UserPojo user : database.getUsers()) {
            if (user.getUsername().equals(username)) {
                // 2. Tenta remover o cliente cujo ID coincida com o fornecido
                boolean removed = user.getClientes().removeIf(client -> client.getId() == clientId);

                if (removed) {
                    // 3. Se algo foi removido, persiste a alteração no disco
                    dao.saveDatabase(database);
                    return true;
                }
            }
        }
        return false;
    }
}



