package com.formation.paradise.model;

import java.io.Serializable;

public class Place implements Serializable {

    private String name;
    private Long id;

    public Place(String name, Long id){
        this.name = name;
        this.id = id;
    }

    public Place(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
