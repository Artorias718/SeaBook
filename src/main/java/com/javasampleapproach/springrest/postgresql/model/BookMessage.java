package com.javasampleapproach.springrest.postgresql.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BookMessage implements Serializable {
    private Date dataPrenotazione;
    private List<Integer> listaPosti;

    public Date getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(Date dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public List<Integer> getListaPosti() {
        return listaPosti;
    }

    public void setListaPosti(List<Integer> listaPosti) {
        this.listaPosti = listaPosti;
    }
}
