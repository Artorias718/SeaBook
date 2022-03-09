package com.javasampleapproach.springrest.postgresql.model;

import javax.persistence.*;

@Entity
@Table(name = "spot")
public class Spot {
    @Id
    @Column(name = "spotId", nullable = false)
    private Long idd;

    @Column(name = "sid")
    private long sid;

    @Column(name = "isBooked")
    private boolean isBooked;

    @Column(name = "price")
    private double price;

    public Long getId() {
        return idd;
    }
    public void setId(Long idd) {
        this.idd = idd;
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
    }







}