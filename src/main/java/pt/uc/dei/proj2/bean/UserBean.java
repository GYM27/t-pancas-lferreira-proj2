package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.uc.dei.proj2.dao.DatabaseDao;
import pt.uc.dei.proj2.pojo.DatabasePojo;
import pt.uc.dei.proj2.pojo.UserPojo;

import java.io.*;
import java.util.ArrayList;


@ApplicationScoped
public class UserBean implements Serializable {
    @Inject
    private DatabaseDao dao; // Injeção do DAO centralizado
    private DatabasePojo database; // Mantém o estado global em memória


    @jakarta.annotation.PostConstruct
    public void init() {
        this.database = dao.loadDatabase();

        System.out.println("DEBUG INIT - USERS CARREGADOS: "
                + (database.getUsers() == null ? "null" : database.getUsers().size()));

        // Garante que a lista existe mesmo que o JSON esteja vazio
        if (this.database.getUsers() == null) {
            this.database.setUsers(new ArrayList<>());
        }
        System.out.println("Utilizadores carregados: " + this.database.getUsers().size());
    }

    public boolean register(UserPojo newUser) {
        if (newUser == null || newUser.getUsername() == null) return false;

        // Verifica duplicados na lista do database
        for (UserPojo user : database.getUsers()) {
            if (user.getUsername().equalsIgnoreCase(newUser.getUsername())) return false;
        }

        newUser.setLeads(new ArrayList<>());
        newUser.setClientes(new ArrayList<>());
        database.getUsers().add(newUser);

        save(); // Chama o método de persistência interno
        return true;
    }

    public boolean login(String username, String password) {
        // 1. Verificação básica de segurança
        if (username == null || password == null || database.getUsers() == null) {
            return false;
        }

        // 2. O teu ciclo FOR tradicional (compatível com o que sabes)
        for (UserPojo u : database.getUsers()) {
            if (u.getUsername().equalsIgnoreCase(username) &&
                    u.getPassword().equals(password)) {
                return true; // Encontrou e a password bate certo!
            }
        }

        return false; // Não encontrou ninguém com essas credenciais
    }

    public UserPojo getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        for (UserPojo u : database.getUsers()) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    public boolean updateUser(String username, UserPojo updatedData) {
        for (UserPojo u : database.getUsers()) {
            if (u.getUsername().equalsIgnoreCase(username)) {

                System.out.println("DEBUG PHOTO RECEBIDA: " + updatedData.getPhoto());

                // Atualizamos os campos que o POJO possui
                u.setFirstName(updatedData.getFirstName());
                u.setLastName(updatedData.getLastName());
                u.setEmail(updatedData.getEmail());
                u.setCellphone(updatedData.getCellphone());
                u.setPhoto(updatedData.getPhoto()); // URL da fotografia

                // Atualiza a password se ela for enviada
                if (updatedData.getPassword() != null && !updatedData.getPassword().isEmpty()) {
                    u.setPassword(updatedData.getPassword());
                }

                save(); // Persiste no ficheiro JSON
                return true;
            }
        }
        return false;
    }

    public void save() {
        dao.saveDatabase(this.database); // Delega a gravação ao DAO
    }

}


