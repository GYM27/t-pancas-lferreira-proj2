package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import pt.uc.dei.proj2.dto.LoginDto;
import pt.uc.dei.proj2.pojo.UserPojo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserBean implements Serializable {

    @Inject
    LoginBean loginBean;

    // le o ficheiro de dados
    private final String filename = System.getProperty("user.home") + File.separator + "dataBase.json";
    private List<UserPojo> users = new ArrayList<>();

    // incia o leitura do ficheiro de dados e o ponto de partida do UserBean

    @jakarta.annotation.PostConstruct
    public void init() {
        // cria uma referencia variavel para comfirmar se o ficheiro existe
        File f = new File(filename);

        //verifica se existe se sim carrega senao faz uma nova lista de users
        if (f.exists()) {
            try {
                // Carrega os utilizadores existentes para a memória
                FileReader fileReader = new FileReader(f);
                this.users = JsonbBuilder.create().fromJson(fileReader,new ArrayList<UserPojo>(){}.getClass().getGenericSuperclass());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else
            this.users = new ArrayList<>();
    }

    // metodo para registar novo User
    public boolean register(UserPojo newUser) {

        // 1. Verificação de segurança(verifica se o user for null ou o campo usarname estiver vazio para garantir que poximo passo so e efetuado se existir user.
        if (newUser == null || newUser.getUsername() == null) {
            return false;
        }
        // Procurar na lista carregada do JSON se o username existe se existe para para nao repetir username
        for (UserPojo user : users) {
            if (user.getUsername().equalsIgnoreCase(newUser.getUsername())) {
                return false;
            }
        }
            // Apos verificar tudo ele cria novo user carregando as duas listas e cria o user
            newUser.setLeads(new ArrayList<>());
            newUser.setClientes(new ArrayList<>());

            users.add(newUser);

            return writeIntoJsonFile();
        }

    // metodo para login
    public boolean login(LoginDto loginUserDetails) {
        // verifica se user existe
        if (loginUserDetails == null) {
            return false;
        }
        // percorre a lista carregada em init
        for (UserPojo user : users) {
            if (user.getUsername().equalsIgnoreCase(loginUserDetails.getUsername()) && user.getPassword().equals(loginUserDetails.getPassword())) {
                loginBean.setCurrentUserPojo(user);
                return true;
            }
        }
        return false;
    }

    // metodo para guardar os dados
    private boolean writeIntoJsonFile() {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            try (FileOutputStream fos = new FileOutputStream(new File(filename))) {
                jsonb.toJson(this.users, fos);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserPojo getUserByUsername(String username) {

        for (UserPojo user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }

        return null;
    }

    public void save() {
        writeIntoJsonFile();
    }

}


