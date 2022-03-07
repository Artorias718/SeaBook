package com.javasampleapproach.springrest.postgresql.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stabilimento")
public class Stabilimento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "posti")
    private int posti;

    //costruttori
    public Stabilimento() {
    }
    public Stabilimento(String name, int posti ) {
        this.name = name;
        this.posti = posti;
    }


    public long getId() {
        return id;
    }

    public void setName(String name) {this.name = name;}
    public String getName() {
        return this.name;
    }

    public void setNumeroPosti(int posti) {this.posti = posti;}
    public Integer getNumeroPosti() {return this.posti;} //NOTA: il nome del metodo è il norme del parametro nelle richieste (postaman e non) con l'iniziale minuscola
}


