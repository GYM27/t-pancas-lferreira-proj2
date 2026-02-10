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

    //listas de clientes e leads

    private List<ClientesPojo> clientes= new ArrayList<>();
    private List<LeadsPojo> leads= new ArrayList<>();


}
