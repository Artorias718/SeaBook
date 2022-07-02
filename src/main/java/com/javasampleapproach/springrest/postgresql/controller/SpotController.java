package com.javasampleapproach.springrest.postgresql.controller;

import com.javasampleapproach.springrest.postgresql.model.*;
import com.javasampleapproach.springrest.postgresql.repo.SpotRepository;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import com.javasampleapproach.springrest.postgresql.services.SpotService;
import com.javasampleapproach.springrest.postgresql.services.StabilimentoService;
import lombok.RequiredArgsConstructor;

import org.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SpotController {

    @Autowired
    SpotRepository repository;

    @Autowired
    StabilimentoRepository stab_repository;

    private final SpotService spotService;

    private final StabilimentoService stabService;

    @GetMapping("/stabilimenti/{sid}/lista_Posti")
    public ResponseEntity<List<Spot>> getAllSpots(@PathVariable long sid) {

        return new ResponseEntity<>(spotService.getAllSpots(sid), HttpStatus.OK);
    }

    @GetMapping("/spots/{pid}")
    public ResponseEntity<Optional<Spot>> getSpot(@PathVariable long pid){

        return new ResponseEntity<>(spotService.getSpot(pid), HttpStatus.OK);
    }

    @PostMapping("/stabilimenti/{sid}/create_spot")
    public ResponseEntity<Spot> postSpotInStabilimento(@PathVariable long sid, @RequestBody Spot spot){

        return new ResponseEntity<>(spotService.createSpot(sid, spot), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/stabilimenti/{sid}/delete_spots")
    public ResponseEntity<String> deleteAllSpotsInStab(@PathVariable long sid) {

        return new ResponseEntity<>(spotService.deleteAllSpotsInStab(sid), HttpStatus.OK);
    }

    @DeleteMapping("/stabilimenti/{sid}/{pid}/delete")
    public ResponseEntity<String> deleteSpot(@PathVariable long sid, @PathVariable long pid) {


        return new ResponseEntity<>(spotService.deleteSpot(pid), HttpStatus.OK);

    }

    @PutMapping("/stabilimenti/{sid}/{pid}/put")
    public ResponseEntity<Spot> updateSpot(@PathVariable("sid") long id, @RequestBody Spot spot) {

        Optional<Spot> spotData = spotService.getSpot(id);

        if (spotData.isPresent()) {
            Spot _spot = spotData.get();
            _spot.IsBooked(spot.IsBooked());
            _spot.setPrice(spot.getPrice());
            _spot.setStabId(spot.getStabId());
            return new ResponseEntity<>(spotService.createSpot(null,_spot), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/stabilimenti/{sid}/lista_Posti/{selectedDate}")
    public ResponseEntity<List<Spot>> getAllSpotsFlagged(@PathVariable long sid, @PathVariable String selectedDate) throws JSONException {

        return new ResponseEntity<>(spotService.getAllSpotsFlagged(sid, selectedDate), HttpStatus.OK);
    }

}