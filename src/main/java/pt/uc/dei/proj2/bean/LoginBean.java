package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import pt.uc.dei.proj2.pojo.UserPojo;

import java.io.Serializable;
import java.util.ArrayList;

@SessionScoped
public class LoginBean implements Serializable {

    UserPojo currentUserPojo;
    public LoginBean() {}
    public LoginBean(UserPojo currentUserPojo) {
        this.currentUserPojo = currentUserPojo;
    }
    public UserPojo getCurrentUserPojo() {
        return currentUserPojo;
    }
    public void setCurrentUserPojo(UserPojo currentUserPojo) {
        this.currentUserPojo = currentUserPojo;
    }



}
