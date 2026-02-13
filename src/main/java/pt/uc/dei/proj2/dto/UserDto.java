package pt.uc.dei.proj2.dto;

import java.io.Serializable;

public class UserDto implements Serializable {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String image;
    private String cellphone;

    public UserDto() {
    }

    public UserDto(String username, String password, String email, String firstName, String ultimo_nome, String image, String cellphone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.cellphone = cellphone;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImagem() {
        return image;
    }
    public void setImagem(String image) {
        this.image = image;
    }

    public String getCellphone() {
        return cellphone;
    }
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}