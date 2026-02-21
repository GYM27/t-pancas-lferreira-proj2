package pt.uc.dei.proj2.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import pt.uc.dei.proj2.pojo.DatabasePojo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Serializable;

/**
 * Classe de Acesso a Dados (DAO) responsável pela persistência da base de dados.
 * Gere a leitura e escrita do ficheiro JSON que armazena os utilizadores e clientes.
 */
@ApplicationScoped
public class DatabaseDao implements Serializable {

    /** Caminho relativo para o ficheiro de base de dados JSON. */
    private final String FILE_PATH =
            System.getProperty("jboss.server.base.dir")
                    + File.separator + "dataBase.json";

    /** Motor JSON-B para conversão entre objetos Java e formato JSON. */
    private final Jsonb jsonb = JsonbBuilder.create();

    /**
     * Carrega a base de dados a partir do ficheiro JSON.
     * Converte a estrutura do ficheiro no objeto global DatabasePojo.
     * * @return {@link DatabasePojo} contendo os dados carregados ou uma nova instância vazia se o ficheiro não existir.
     */
    public DatabasePojo loadDatabase() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new DatabasePojo(); // Retorna uma base vazia se o ficheiro não existir
        }

        try (FileReader reader = new FileReader(file)) {
            // Desserializa o ficheiro inteiro para o objeto envelope
            return jsonb.fromJson(reader, DatabasePojo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabasePojo();
        }
    }

    /**
     * Guarda o estado atual da base de dados no ficheiro JSON.
     * Utiliza serialização para converter o objeto Java em texto formatado.
     * * @param database O objeto {@link DatabasePojo} contendo todos os dados a serem persistidos.
     */
    public void saveDatabase(DatabasePojo database) {

        System.out.println("user.dir = " + System.getProperty("user.dir"));
        System.out.println("FILE_PATH = " + FILE_PATH);
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {

            System.out.println("A guardar em: " + new File(FILE_PATH).getAbsolutePath());
            // O toJson converte o objeto para texto e o FileOutputStream guarda-o
            jsonb.toJson(database, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}