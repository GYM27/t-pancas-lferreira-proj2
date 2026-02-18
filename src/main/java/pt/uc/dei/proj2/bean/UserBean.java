package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.uc.dei.proj2.dao.DatabaseDao;
import pt.uc.dei.proj2.dto.LoginDto;
import pt.uc.dei.proj2.pojo.DatabasePojo;
import pt.uc.dei.proj2.pojo.UserPojo;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;


@ApplicationScoped
public class UserBean implements Serializable {
    @Inject
    private DatabaseDao dao; // Injeção do DAO centralizado

    @Inject
    private LoginBean loginBean;
    private DatabasePojo database; // Mantém o estado global em memória

    @jakarta.annotation.PostConstruct
    public void init() {
        this.database = dao.loadDatabase(); // Carrega via DAO
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

    public void save() {
        dao.saveDatabase(this.database); // Delega a gravação ao DAO
    }

    // metodo para login
    public boolean login(LoginDto loginUserDetails) {
        // verifica se user existe
        if (loginUserDetails == null) {
            return false;
        }
        // percorre a lista carregada em init
        for (UserPojo user : database.getUsers()) {
            if (user.getUsername().equalsIgnoreCase(loginUserDetails.getUsername()) && user.getPassword().equals(loginUserDetails.getPassword())) {
                loginBean.setCurrentUserPojo(user);
                return true;
            }
        }
        return false;
    }

    public UserPojo getUserByUsername(String username) {
        // 1. Verificação de segurança inicial
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        for (UserPojo user : database.getUsers()) {
            // 2. Verificação de segurança interna e lógica de negócio
            if (user.getUsername() != null && user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }


}


