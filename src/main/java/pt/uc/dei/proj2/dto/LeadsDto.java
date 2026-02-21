package pt.uc.dei.proj2.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

public class LeadsDto {

    private Integer id;
    private String title;
    private String description;
    private Integer state;
    private String date;

    //Construtor vazio para receber o JSON e converter em Java
    public LeadsDto() {
    }

    public LeadsDto(Integer id, String title, String description, Integer state, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}