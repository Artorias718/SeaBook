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


    public Long getId() {
        return id;
    }
    public void setId(Long idd) {
        this.id = idd;
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

    private Stabilimento() {
    }

    public Stabilimento(String name, int capacity, String address, String phoneNumber) {
        this.name = name;
        this.capacity = capacity;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Stabilimento{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", capacity='" + capacity + '\'' +
            ", address='" + address + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            '}';
    }

}


