package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.bind.JsonbBuilder;
import pt.uc.dei.proj2.dto.ClientesDto;
import pt.uc.dei.proj2.pojo.UserPojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;

@ApplicationScoped //cria apenas uma instância daquela classe para toda a aplicação enquanto ela estiver a correr
public class ClientesBean implements Serializable {

    final String filename = "database.json";
    private ArrayList<ClientesDto> clientes;
    private ArrayList<UserPojo> userPojos;

    public ClientesBean() {
        File f = new File(filename);
        if (f.exists()) {
            try {
                FileReader filereader = new FileReader(f);
                clientes = JsonbBuilder.create().fromJson(filereader, new ArrayList<ClientesDto>() {
                }.getClass().getGenericSuperclass());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else
            clientes = new ArrayList<ClientesDto>();

        userPojos = new ArrayList<UserPojo>();

    }


}
