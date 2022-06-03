package com.javasampleapproach.springrest.postgresql.controller;

import com.javasampleapproach.springrest.postgresql.model.*;
import com.javasampleapproach.springrest.postgresql.repo.SpotRepository;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class SpotController {

    @Autowired
    SpotRepository repository;
    @Autowired
    StabilimentoRepository stab_repository;

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

        //TODO verificare se funziona dopo aver risolto con il db
        Spot newspot = repository.save(new Spot(sid, spot.getPrice()));
        Optional<Stabilimento> stab = stab_repository.findById(sid);

        if (stab.isPresent()) {
            Stabilimento s = stab.get();
            s.increaseCapacity();
            stab_repository.save(s);
        }

        return newspot;
    }

    @PostMapping("/stabilimenti/{sid}/create_spots")
    public Spot postSpotInStabilimento(@PathVariable long sid, @RequestBody String jsonSpotList){

        try {
            //JSONObject spotList = new JSONObject(jsonSpotList);
            JSONObject jsonObject = new JSONObject(jsonSpotList.trim());
            Iterator<String> keys = jsonObject.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                if (jsonObject.get(key) instanceof JSONObject) {
                    // do something with jsonObject here
                    System.out.println(key);
                }
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

//        Spot newspot = repository.save(new Spot(sid, spot.getPrice()));
//        Optional<Stabilimento> stab = stab_repository.findById(sid);
//
//        if (stab.isPresent()) {
//            Stabilimento s = stab.get();
//            s.increaseCapacity();
//            stab_repository.save(s);
//        }
//
//        return newspot;
        return null;
    }

    @Transactional
    @DeleteMapping("/stabilimenti/{sid}/delete_spots")
    public ResponseEntity<String> deleteAllSpotsInStab(@PathVariable long sid) {

        repository.deleteAllBySid(sid);

        return new ResponseEntity<>("All spots in this stab have been deleted!", HttpStatus.OK);

        //TODO Decrementare il numero posti dello stabilimento
    }

    @DeleteMapping("/stabilimenti/{sid}/{pid}/delete")
    public ResponseEntity<String> deleteSpot(@PathVariable long sid, @PathVariable long pid) {

        repository.deleteById(pid);

        return new ResponseEntity<>("The spot have been deleted!", HttpStatus.OK);

        //TODO Decrementare il numero posti dello stabilimento
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

    @RabbitListener(queues = "bookingQueue")
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

    @RabbitListener(queues = "debookingQueue")
    public void listener2(List<Integer> message) {


        for (Integer i : message) {

            Optional<Spot> old_spot = repository.findById(Long.valueOf(i));

            if (old_spot.isPresent()) {
                Spot new_spot = old_spot.get();
                new_spot.IsBooked(false);
                repository.save(new_spot);
            }


        }


    }

    }