package com.javasampleapproach.springrest.postgresql.model;

import javax.persistence.*;

@Entity
@Table(name = "spot")
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sid")
    private long sid;

    @Column(name = "isBooked")
    private boolean isBooked;

    @Column(name = "price")
    private double price;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "spotRow")
    private int row;

    @Column(name = "spotColumn")
    private int column;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public long getStabId() {
        return sid;
    }
    public void setStabId(long sid) {
        this.sid = sid;
    }

    public boolean isBooked() {
        return isBooked;
    }
    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    private Spot(){
    }

    public Spot(long sid, double price){
        this.sid = sid;
        this.price = price;
        this.isBooked = false;
    }

    public Spot(long sid, double price, int row, int column) {
        this.sid = sid;
        this.isBooked = false;
        this.price = price;
        this.isActive = true;
        this.row = row;
        this.column = column;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }

    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "id=" + id +
                ", sid=" + sid +
                ", isBooked=" + isBooked +
                ", price=" + price +
                ", isActive=" + isActive +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}