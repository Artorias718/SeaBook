package com.javasampleapproach.springrest.postgresql.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "spot")
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "spotId", nullable = false)
    private Long id;

    @Column(name = "sid")
    private long sid;

    /**
     * attribute che mantiene tutte le date per cui il posto e' prenotato
     */
    // TODO(1) map data boolean
    @ElementCollection
    @Column(name = "datePrenotate")
    private List<Date> datePrenotate;

    public List<Date> getDatePrenotate() {
        return datePrenotate;
    }

    public void setDatePrenotate(List<Date> datePrenotate) {
        this.datePrenotate = datePrenotate;
    }

    @JsonProperty("isBooked")
    @Column(name = "isBooked")
    private boolean isBooked;

    @Column(name = "price")
    private double price;


    public Long getId() {
        return id;
    }

    public long getStabId() {
        return sid;
    }
    public void setStabId(long sid) {
        this.sid = sid;
    }

    public boolean IsBooked() {
        return isBooked;
    }
    public void IsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public Spot(){

    }

    public Spot(long sid, double price){
        this.sid = sid;
        this.price = price;
        this.isBooked = false;
        //datePrenotate = new ArrayList<>();
    }










}