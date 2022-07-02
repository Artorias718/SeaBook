package com.javasampleapproach.springrest.postgresql.controller;

import com.javasampleapproach.springrest.postgresql.model.*;
import com.javasampleapproach.springrest.postgresql.repo.SpotRepository;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import com.javasampleapproach.springrest.postgresql.services.SpotService;
import com.javasampleapproach.springrest.postgresql.services.StabilimentoService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
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

import static com.javasampleapproach.springrest.postgresql.Utils.Utils.extractAndFormatDate;

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

        for (Spot s: posti) {
            List<Date> datesList = s.getDatePrenotate();
            if (datesList.contains(date)) {
                s.IsBooked(true);
            }
        }

        return posti;
    }
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


}