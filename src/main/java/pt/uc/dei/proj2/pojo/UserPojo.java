package pt.uc.dei.proj2.pojo;

import java.util.ArrayList;
import java.util.List;

public class UserPojo {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String photo;
    private String cellphone;

    //Inicializar as listas evita NullPointerException

    private List<ClientesPojo> clients = new ArrayList<>();
    private List<LeadsPojo> leads = new ArrayList<>();

    // Construtor vazio é obrigatório para o JSON Binding funcionar
    public UserPojo() {
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhoto() {
        return photo;
    }
    public String getEmail() {
        return email;
    }
    public String getCellphone() { return cellphone; }

    public List<ClientesPojo> getClientes() {
        return clients;
    }
    public List<LeadsPojo> getLeads() {
        return leads;
    }


    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void setClientes(List<ClientesPojo> clientes) {
        this.clients = clientes;
    }
    public void setLeads(List<LeadsPojo> leads) {
        this.leads = leads;
    }


}


