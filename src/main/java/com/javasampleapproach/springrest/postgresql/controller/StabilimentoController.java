package com.javasampleapproach.springrest.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.javasampleapproach.springrest.postgresql.model.Customer;
import com.javasampleapproach.springrest.postgresql.model.Posto;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.PostoRepository;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class StabilimentoController {

    @Autowired
    StabilimentoRepository repository;

    @Autowired
    PostoRepository lista_posti;

    @GetMapping("/stabilimenti")
    public List<Stabilimento> getAllStabilimenti() {

        List<Stabilimento> stabilimenti = new ArrayList<>();
        repository.findAll().forEach(stabilimenti::add);

        return stabilimenti;
    }

    @PostMapping(value = "/stabilimenti/create")
    public Stabilimento postStabilimento(@RequestBody Stabilimento stabilimento) {

        Stabilimento _stabilimento = repository.save(new Stabilimento(stabilimento.getName(), stabilimento.getNumeroPosti()));
        return _stabilimento;
    }

    /*@PostMapping(value = "/stabilimenti/{id}/addPosto")
    public void postPostoInStabilimento(@RequestBody Posto posto, @PathVariable("id") long id) {

        //manca dire a quale stabilimento, tramite l'id
        Posto _posto = lista_posti.save(new Posto());
    }*/

    @GetMapping("/stabilimenti/{sid}/listaPosti")
    public List<Posto> getAllPosti(@PathVariable long sid) {

        //questa mi servirà da altre parti per accedere al singolo stabilimento
        //Stabilimento x = repository.findById(id);

        List<Posto> posticini = new ArrayList<>();
        lista_posti.findAllBySid(sid).forEach(posticini::add);

        return posticini;
    }


}
