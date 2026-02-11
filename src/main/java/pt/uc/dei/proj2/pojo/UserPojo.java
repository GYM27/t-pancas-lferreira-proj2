package pt.uc.dei.proj2.pojo;

import java.util.ArrayList;
import java.util.List;

public class UserPojo {

    private String username;
    private String password;
    private String email;
    private String primeiro_nome;
    private String ultimo_nome;
    private String imagem;


    public UserPojo() {} // important

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setPrimeiro_nome(String primeiro_nome) { this.primeiro_nome = primeiro_nome; }
    public void setUltimo_nome(String ultimo_nome) { this.ultimo_nome = ultimo_nome; }
    public void setImagem(String imagem) { this.imagem = imagem; }
    public void setClientes(List<ClientesPojo> clientes) { this.clientes = clientes; }
    public void setLeads(List<LeadsPojo> leads) { this.leads = leads; }

    //listas de clientes e leads

    private List<ClientesPojo> clientes= new ArrayList<>();
    private List<LeadsPojo> leads= new ArrayList<>();


//    public String getUsername() {
//    }
//
//    public String getPassword() {
//    }

    public String getEmail() {
        return email;
    }
}
