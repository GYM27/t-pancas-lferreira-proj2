package pt.uc.dei.proj2.dto;

public class LoginDto {
    private String username;
    private String password;

    // Construtor vazio obrigatório para o JSON Binding
    public LoginDto() {}

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}