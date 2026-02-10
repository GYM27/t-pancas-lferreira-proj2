package pt.uc.dei.proj2.pojo;

public class LeadsPojo {

    private int id;
    private String title;
    private String description;
    private String state;
    private String date;

    //Construtor vazio para criar objetos dinamicamente e serializar/deserializar JSON
    public LeadsPojo(){

    }

    public LeadsPojo(int id, String title, String description, String state, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.date = date;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
