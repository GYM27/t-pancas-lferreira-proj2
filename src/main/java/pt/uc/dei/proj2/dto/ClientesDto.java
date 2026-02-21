package pt.uc.dei.proj2.dto;

import java.util.UUID;

public class ClientesDto {

    private String organizao;
    private String nome;
    private String email;
    private String numero_telefone;
    private UUID id;
    

    public ClientesDto() {
    }

    public ClientesDto(String organizao, String nome, String email, String numero_telefone) {
        this.organizao = organizao;
        this.nome = nome;
        this.email = email;
        this.numero_telefone = numero_telefone;
        this.id = UUID.randomUUID();
    }

    // Getters e Setters necessários para a conversão JSON

    public String getOrganizao() {
        return organizao;
    }

    public void setOrganizao(String organizao) {
        this.organizao = organizao;
    }

    public String getNome_responsavel() {
        return nome;
    }

    public void setNome_responsavel(String nome) {
        this.nome = nome;
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

    public UUID getId() {
        return id;
    }
    
}