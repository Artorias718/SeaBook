package com.javasampleapproach.springrest.postgresql.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
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

    @ElementCollection
    @Column(name = "datePrenotate")
    private List<Date> datePrenotate;

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
    public List<Date> getDatePrenotate() {
        return datePrenotate;
    }
    public void setDatePrenotate(List<Date> datePrenotate) {
        this.datePrenotate = datePrenotate;
    }


    public Spot(){

    }
    public Spot(long sid, double price){
        this.sid = sid;
        this.price = price;
        this.isBooked = false;
    }










}