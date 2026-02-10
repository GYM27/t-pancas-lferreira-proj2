package pt.uc.dei.proj2.dto;

public class ClientesDto {

    private String organizao;
    private String nome_responsavel;
    private String email;
    private String numero_telefone;
    private int id;

    public ClientesDto() {
    }

    public ClientesDto(String organizao, String nome_responsavel, String email, String numero_telefone, int id) {
        this.organizao = organizao;
        this.nome_responsavel = nome_responsavel;
        this.email = email;
        this.numero_telefone = numero_telefone;
        this.id = id;
    }

    // Getters e Setters necessários para a conversão JSON

    public String getOrganizao() {
        return organizao;
    }

    public void setOrganizao(String organizao) {
        this.organizao = organizao;
    }

    public String getNome_responsavel() {
        return nome_responsavel;
    }

    public void setNome_responsavel(String nome_responsavel) {
        this.nome_responsavel = nome_responsavel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero_telefone() {
        return numero_telefone;
    }

    public void setNumero_telefone(String numero_telefone) {
        this.numero_telefone = numero_telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}