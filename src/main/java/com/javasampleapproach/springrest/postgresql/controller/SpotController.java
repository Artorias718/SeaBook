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

    static final String queueName = "spring-boot";

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

    @PutMapping("/stabilimenti/{id}/{sid}/put")
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

    // TODO(3) il messaggio dev'essere del tipo data e lista di posti
    @Transactional
    @RabbitListener(queues = "bookingQueue")
    public void listener(String message) throws JSONException, ParseException {

        // BookMessage bookMessage = (BookMessage) message;
        System.out.println("\n\n\n\n\n\n\n\n" + message + "\n");
        JSONObject obj = new JSONObject(message);

        String dataPrenotMsg = obj.getString("dataPrenotazione");
        System.out.println(dataPrenotMsg + "\n\n\n");

        SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        Date d1 = null;
        try{
            d1 = sdf3.parse(dataPrenotMsg);

        }catch (Exception e){ e.printStackTrace(); }


        System.out.println("check..." + d1);

        JSONArray listaPosti = obj.getJSONArray("listaPosti");
        System.out.println(listaPosti + "\n\n\n\n");

        for (int i = 0; i < listaPosti.length(); i++) {
            Long id = listaPosti.getLong(i);
            Optional<Spot> spot = repository.findById(id);

            if (spot.isPresent()) {
                Spot _spot = spot.get();
                System.out.println("\nSpot: " + _spot.getId());
                List<Date> datePrenotate = _spot.getDatePrenotate();
                datePrenotate.add(d1);
                System.out.println("\ndate prenotate: " + _spot.getDatePrenotate() + "\n\n\n\n\n");
                _spot.setDatePrenotate(datePrenotate);

                repository.save(_spot);
            }
        }

//        for (Integer i : listaPosti) {
//            Long id = Long.valueOf(i);
//            Optional<Spot> spot = repository.findById(id);
//
//                if (spot.isPresent()) {
//                Spot _spot = spot.get();
//                System.out.println("\n\n\n\n\nSpot: " + _spot.getId() + "\n\n\n\n\n");
//                List<Date> datePrenotate = _spot.getDatePrenotate();
////                datePrenotate.add(dateWithoutTime);
//                System.out.println("\n\n\n\n\ndate prenotate: " + _spot.getDatePrenotate());
//                _spot.setDatePrenotate(datePrenotate);
//
//                repository.save(_spot);
//            }
//        }

        //        Calendar rightNow = Calendar.getInstance();
//        rightNow.set(Calendar.HOUR_OF_DAY, 0);
//        rightNow.set(Calendar.MINUTE, 0);
//        rightNow.set(Calendar.SECOND, 0);
//        rightNow.set(Calendar.MILLISECOND, 0);
//        Date dateWithoutTime = rightNow.getTime();
//        System.out.println("\n\n\n\n\n\n\n\n" + dateWithoutTime + "\n\n\n\n\n\n\n\n");
//
//        for (Integer i : message) {
//            Long id = Long.valueOf(i);
//            Optional<Spot> spot = repository.findById(id);
//
//            if (spot.isPresent()) {
//                Spot _spot = spot.get();
//                System.out.println("\n\n\n\n\nSpot: " + _spot.getId() + "\n\n\n\n\n");
//                List<Date> datePrenotate = _spot.getDatePrenotate();
//                datePrenotate.add(dateWithoutTime);
//                System.out.println("\n\n\n\n\ndate prenotate: " + _spot.getDatePrenotate());
//                _spot.setDatePrenotate(datePrenotate);
//
//                repository.save(_spot);
//            }
//        }
    }
//    public void listener(List<Integer> message) {
//
//        for (Integer i : message) {
//            Optional<Spot> old_spot = repository.findById(Long.valueOf(i));
//
//            if (old_spot.isPresent()) {
//                Spot new_spot = old_spot.get();
//                new_spot.IsBooked(true);
//                repository.save(new_spot);
//            }
//
//        }
//
//
//    }

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