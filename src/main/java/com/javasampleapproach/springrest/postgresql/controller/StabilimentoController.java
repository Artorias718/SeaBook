package com.javasampleapproach.springrest.postgresql.controller;

import java.util.ArrayList;
import java.util.List;

import com.javasampleapproach.springrest.postgresql.model.Spot;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.SpotRepository;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class StabilimentoController {

    @Autowired
    StabilimentoRepository repository;

    @GetMapping("/stabilimenti")
    public List<Stabilimento> getAllStabilimenti() {

        List<Stabilimento> stabilimenti = new ArrayList<>();
        repository.findAll().forEach(stabilimenti::add);

        return stabilimenti;
    }

    @PostMapping(value = "/stabilimenti/create")
    public Stabilimento postStabilimento(@RequestBody Stabilimento stabilimento) {

        Stabilimento newstab = repository.save(new Stabilimento(stabilimento.getName(), stabilimento.getSpotsNumber(), stabilimento.getAddress(), stabilimento.getPhoneNumber()));
        return newstab;
    }

    //TODO

    //@GetMapping("/stabilimenti/{sid}")    prende uno specifico stabilimento

    //@PutMapping("/stabilimenti/{sid}/put") modifica uno stabilimento

    //@DeleteMapping("/stabilimenti/delete)  cancella tutti gli stabilimenti

    //@DeleteMapping("/stabilimenti/{sid}/delete/") cancello uno stabiliemento specifico












    /*@PostMapping(value = "/stabilimenti/{id}/addPosto")
    public void postPostoInStabilimento(@RequestBody Spot posto, @PathVariable("id") long id) {

        //manca dire a quale stabilimento, tramite l'id
        Spot _posto = lista_posti.save(new Spot());
    }*/




}
