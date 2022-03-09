package com.javasampleapproach.springrest.postgresql.controller;

import com.javasampleapproach.springrest.postgresql.model.Spot;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class SpotController {

    @Autowired
    SpotRepository repository;

    @GetMapping("/stabilimenti/{sid}/listaPosti")
    public List<Spot> getAllSpots(@PathVariable long sid) {

        //questa mi servirà da altre parti per accedere al singolo stabilimento
        //Stabilimento x = repository.findById(id);

        List<Spot> posti = new ArrayList<>();
        repository.findAllBySid(sid).forEach(posti::add);

        return posti;
    }

    @PostMapping("/stabilimenti/{sid}/createSpot")
    public Spot postSpotInStabilimento(@PathVariable long sid, @RequestBody Spot spot){

        Spot newspot = repository.save(new Spot(sid, spot.getPrice()));
        return newspot;

    }

    //TODO

    //@PutMapping("/stabilimenti/{sid}/put")    modifica un posto

    //@DeleteMapping("/stabilimenti/{sid}/delete")  cancella tutti i posti di uno stabilimento

    //@DeleteMapping("/stabilimenti/{sid}/delete/{pid}")  cancella uno specifico posto




}