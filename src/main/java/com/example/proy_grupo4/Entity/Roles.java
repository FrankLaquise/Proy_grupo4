package com.example.proy_grupo4.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @Column(name = "idroles", nullable = false)
    private Integer id;

    @Column(name = "tirulo", nullable = false, length = 45)
    private String tirulo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTirulo() {
        return tirulo;
    }

    public void setTirulo(String tirulo) {
        this.tirulo = tirulo;
    }

}