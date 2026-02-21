package pt.uc.dei.proj2.dto;

import java.util.UUID;

public class ClientesDto {

    private String organizacao;
    private String nome;
    private String email;
    private String telefone;
    private UUID id;
    

    public ClientesDto() {
    }

    public ClientesDto(String organizao, String nome, String email, String telefone) {
        this.organizacao = organizao;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.id = UUID.randomUUID();
    }

    // Getters e Setters necessários para a conversão JSON

    public String getOrganizacao() {
        return organizacao;
    }

    public void setOrganizacao(String organizacao) {
        this.organizacao = organizacao;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public UUID getId() {
        return id;
    }
    
}