package com.formation.paradise.model;

import java.io.Serializable;

public class Trip implements Serializable {

    private Long id;
    private Place departure;
    private Place destination;
    private float price;

    public Trip(Long id, Place departure, Place destination, float price) {
        this.id = id;
        this.departure = departure;
        this.destination = destination;
        this.price = price;
    }

    public Trip(Place departure, Place destination, float price) {
        this.departure = departure;
        this.destination = destination;
        this.price = price;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Place getDeparture() {
        return departure;
    }

    public void setDeparture(Place departure) {
        this.departure = departure;
    }

    public Place getDestination() {
        return destination;
    }

    public void setDestination(Place destination) {
        this.destination = destination;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
