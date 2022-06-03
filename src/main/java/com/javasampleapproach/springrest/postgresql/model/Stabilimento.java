package com.javasampleapproach.springrest.postgresql.model;

import javax.persistence.*;

@Entity
@Table(name = "stabilimento")
public class Stabilimento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "address")
    private String address;

    @Column(name ="phoneNumber")
    private String phoneNumber;

    @Column(name = "gpid")
    private String gpid;

    @Column(name = "rating")
    private double rating;

    @Column(name = "photoRef")
    private final String photoRef;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGpid() {
        return gpid;
    }

    public void setGpid(String gpid) {
        this.gpid = gpid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {this.name = name;}

    public String getName() {
        return this.name;
    }
    public void setSpotsNumber(int capacity) {this.capacity = capacity;}

    public int getSpotsNumber() {return this.capacity;} //NOTA: il nome del metodo è il nome del parametro nelle richieste (postaman e non) con l'iniziale minuscola
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void increaseCapacity(){
        this.capacity++;
    }

    public void decreaseCapacity(){
        this.capacity--;
    }

    public Stabilimento() {
        photoRef = null;
    }

    public Stabilimento(String name, int capacity, String address, String phoneNumber, String gpid, double rating) {
        this.name = name;
        this.capacity = capacity;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gpid = gpid;
        this.rating = rating;
        photoRef = null;
    }

    public Stabilimento(String name) {
        this.name = name;
        photoRef = null;
    }

    public Stabilimento(String name, int capacity, String address, String phoneNumber, String gpid, double rating, String photoRef) {
        this.name = name;
        this.capacity = capacity;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gpid = gpid;
        this.rating = rating;
        this.photoRef = photoRef;
    }

    public String getPhotoRef() {
        return photoRef;
    }
}


