package com.javasampleapproach.springrest.postgresql.model;

import org.junit.Test;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posto")
public class Posto {
    @Id
    @Column(name = "posto_id", nullable = false)
    private Long idd;

    public Long getId() {
        return idd;
    }

    public void setId(Long idd) {
        this.idd = idd;
    }

    @Column(name = "sid")
    private long sid;

    public long getStabId() {
        return sid;
    }

    public void setStabId(long sid) {
        this.sid = sid;
    }


    //costruttori

    public Posto(){

    }






}