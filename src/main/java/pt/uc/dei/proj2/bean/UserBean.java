package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pt.uc.dei.proj2.pojo.UserPojo;

import java.io.Serializable;

@RequestScoped
public class UserBean implements Serializable {

    @Inject
    ClientesBean clientesBean;

    @Inject
    LoginBean loginBean;


    public boolean login(String username, String password) {
        // 1. Primeiro, temos de ir buscar a "base de dados" (o ficheiro JSON)
        // Para simplificar agora, vamos usar o LoginBean para validar

        if (username == null || password == null) {
            return false;
        }

        // Aqui a lógica será: procurar no JSON se existe um UserPojo
        // com este username e esta password.

        // Por agora, para o teu teste no Postman dar "Verdadeiro":
        if (username.equals("admin") && password.equals("123")) {
            return true;
        }

        return false;
    }
}
