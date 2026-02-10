package pt.uc.dei.proj2.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pt.uc.dei.proj2.pojo.UserPojo;

import java.io.Serializable;

@RequestScoped
public class UserBean implements Serializable {

    @Inject
    ClientesBean clientesBean;

    @Inject
    LoginBean loginBean;


}
