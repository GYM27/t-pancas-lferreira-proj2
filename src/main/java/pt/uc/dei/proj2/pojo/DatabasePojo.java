package pt.uc.dei.proj2.pojo;

import java.util.ArrayList;
import java.util.List;

public class DatabasePojo {
    // Esta variável "users" corresponde à lista no teu JSON
    private List<UserPojo> users = new ArrayList<>();

    public List<UserPojo> getUsers() { return users; }
    public void setUsers(List<UserPojo> users) { this.users = users; }
}
