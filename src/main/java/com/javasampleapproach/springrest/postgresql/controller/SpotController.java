package com.javasampleapproach.springrest.postgresql.controller;

import com.javasampleapproach.springrest.postgresql.model.*;
import com.javasampleapproach.springrest.postgresql.repo.SpotRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class SpotController {

    @Autowired
    SpotRepository repository;

    static final String queueName = "spring-boot";

    @GetMapping("/stabilimenti/{sid}/lista_Posti")
    public List<Spot> getAllSpots(@PathVariable long sid) {

        //questa mi servirà da altre parti per accedere al singolo stabilimento
        //Stabilimento x = repository.findById(id);

        List<Spot> posti = new ArrayList<>();
        repository.findAllBySid(sid).forEach(posti::add);

        return posti;
    }


    @PostMapping("/stabilimenti/{sid}/create_spot")
    public Spot postSpotInStabilimento(@PathVariable long sid, @RequestBody Spot spot){


        Spot newspot = repository.save(new Spot(sid, spot.getPrice()));

        //TODO
        //Incrementare il numero posti dello stabilimento


        return newspot;
    }

    @Transactional
    @DeleteMapping("/stabilimenti/{sid}/delete_spots")
    public ResponseEntity<String> deleteAllSpotsInStab(@PathVariable long sid) {

        repository.deleteAllBySid(sid);

        return new ResponseEntity<>("All spots in this stab have been deleted!", HttpStatus.OK);

        //TODO
        //Decrementare il numero posti dello stabilimento
    }

    @DeleteMapping("/stabilimenti/{sid}/{pid}/delete")
    public ResponseEntity<String> deleteSpot(@PathVariable long sid, @PathVariable long pid) {

        repository.deleteById(pid);

        return new ResponseEntity<>("The spot have been deleted!", HttpStatus.OK);

        //TODO
        //Decrementare il numero posti dello stabilimento
    }

    @PutMapping("/stabilimenti/{id}/{sid}/put")
    public ResponseEntity<Spot> updateSpot(@PathVariable("sid") long id, @RequestBody Spot spot) {

        Optional<Spot> spotData = repository.findById(id);

        if (spotData.isPresent()) {
            Spot _spot = spotData.get();
            _spot.IsBooked(spot.IsBooked());
            _spot.setPrice(spot.getPrice());
            _spot.setStabId(spot.getStabId());
            return new ResponseEntity<>(repository.save(_spot), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RabbitListener(queues = queueName)
    public void listener(List<Integer> message) {


        for (Integer i : message) {

            Optional<Spot> old_spot = repository.findById(Long.valueOf(i));

            if (old_spot.isPresent()) {
                Spot new_spot = old_spot.get();
                new_spot.IsBooked(true);
                repository.save(new_spot);
            }


        }


    }

    }