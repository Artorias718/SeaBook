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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class SpotController {

    @Autowired
    SpotRepository repository;
    @Autowired
    StabilimentoRepository stab_repository;

    static final String queueName = "spring-boot";

//    TODO(2) inserire nella request degli spot la data per
//     poter impostare i posti disponibili
    @GetMapping("/stabilimenti/{sid}/lista_Posti/{selectedDate}")
    public List<Spot> getAllSpots(@PathVariable long sid, @PathVariable String selectedDate) {

        //questa mi servirà da altre parti per accedere al singolo stabilimento
        //Stabilimento x = repository.findById(id);

        System.out.println("\n\n\n\n\n" + selectedDate);

        String string = "January 2, 2010";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(selectedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(date);

        List<Spot> posti = new ArrayList<>();
        repository.findAllBySid(sid).forEach(posti::add);

        for (Spot s: posti
             ) {
            List<Date> datesList = s.getDatePrenotate();
            // siccome l'ora non e' la stessa probabilemente non trovera' niente
            if (datesList.contains(date)) {
                s.IsBooked(true);
            }
        }

        return posti;
    }

    // entry point per test add data alla lista di prenotazioni
    // questo sucedera' tramite rabbitmq
    @PostMapping("/spot/{id}/prenota")
    public ResponseEntity<Spot> postSpotSetBookedDate(@PathVariable long id, @RequestBody Date dataPrenotazione) {
        Optional<Spot> spot = repository.findById(id);

        System.out.println("\n\n\n\n\nData dal client: " + dataPrenotazione);

        if (spot.isPresent()) {
            Spot _spot = spot.get();
            List<Date> datePrenotate = _spot.getDatePrenotate();
            datePrenotate.add(dataPrenotazione);
            System.out.println("\n\n\n\n\ndate prenotate: " + _spot.getDatePrenotate());
            _spot.setDatePrenotate(datePrenotate);

            return new ResponseEntity<>(repository.save(_spot), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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

    // TODO(3) il messaggio dev'essere del tipo data e lista di posti
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