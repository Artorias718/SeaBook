package com.javasampleapproach.springrest.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.javasampleapproach.springrest.postgresql.Proxy;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class StabilimentoController {

    @Autowired
    StabilimentoRepository repository;

    @Autowired
    Proxy proxy;

    @GetMapping("/google/{placeId}")
    public String getDetails(@PathVariable String placeId) throws JSONException {

        JSONObject obj = new JSONObject(proxy.getDetails(placeId));
        String a = obj.getJSONObject("result").getString("name");
        System.out.println(a);


        return proxy.getDetails(placeId);

    }

    @GetMapping("/stabilimenti")
    public List<Stabilimento> getAllStabilimenti() throws JSONException {

        List<Stabilimento> stabilimenti = new ArrayList<>();
        repository.findAll().forEach(stabilimenti::add);

        for(Stabilimento stab : stabilimenti){
            updateRating(stab.getId());
        }

        return stabilimenti;
    }

    @PostMapping(value = "/stabilimenti/create")
    public Stabilimento postStabilimento(@RequestBody Stabilimento stabilimento) {

        Stabilimento newstab = repository.save(
                new Stabilimento(
                        stabilimento.getName(),
                        stabilimento.getSpotsNumber(),
                        stabilimento.getAddress(),
                        stabilimento.getPhoneNumber(),
                        stabilimento.getGpid(),
                        stabilimento.getRating()));
        return newstab;
    }
    @PostMapping(value = "/stabilimenti/create/{placeId}")
    public Stabilimento postStabilimentoByGoogle(@PathVariable String placeId) throws JSONException {

        JSONObject obj = new JSONObject(proxy.getDetails(placeId));
        Stabilimento newstab = repository.save(
                new Stabilimento(
                        obj.getJSONObject("result").getString("name"),
                        0,
                        obj.getJSONObject("result").getString("formatted_address"),
                        obj.getJSONObject("result").getString("formatted_phone_number"),
                        obj.getJSONObject("result").getString("place_id"),
                        obj.getJSONObject("result").getDouble("rating")));
        return newstab;
    }

    @GetMapping("/stabilimenti/{id}")
    public Optional<Stabilimento> getStabilimento(@PathVariable long id) throws JSONException {

        updateRating(id);
        return repository.findById(id);

    }

    @DeleteMapping("/stabilimenti/{id}/delete")
    public ResponseEntity<String> deleteStabilimento(@PathVariable("id") long id) {

        repository.deleteById(id);

        return new ResponseEntity<>("Stab has been deleted!", HttpStatus.OK);
    }

    @DeleteMapping("/stabilimenti/delete")
    public ResponseEntity<String> deleteAllStabilimenti() {

        repository.deleteAll();

        return new ResponseEntity<>("All stabilimenti have been deleted!", HttpStatus.OK);
    }

    @PutMapping("/stabilimenti/{id}/put")
    public ResponseEntity<Stabilimento> updateStabilimento(@PathVariable("id") long id, @RequestBody Stabilimento stabilimento) {

        Optional<Stabilimento> stabData = repository.findById(id);

        if (stabData.isPresent()) {
            Stabilimento _stab = stabData.get();
            _stab.setName(stabilimento.getName());
            _stab.setSpotsNumber(stabilimento.getSpotsNumber());
            _stab.setAddress(stabilimento.getAddress());
            _stab.setPhoneNumber(stabilimento.getPhoneNumber());
            _stab.setGpid(stabilimento.getGpid());
            _stab.setRating(stabilimento.getRating());
            return new ResponseEntity<>(repository.save(_stab), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/stabilimenti/{id}/putCapacity/{capacity}")
    public ResponseEntity<Stabilimento> updateStabilimento(@PathVariable("id") long id, @PathVariable ("capacity") int capacity) {

        Optional<Stabilimento> stabData = repository.findById(id);

        if (stabData.isPresent()) {
            Stabilimento _stab = stabData.get();
            _stab.setSpotsNumber(capacity);
            return new ResponseEntity<>(repository.save(_stab), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public void updateRating(long id) throws JSONException {

        Optional<Stabilimento> stabData = repository.findById(id);
        Stabilimento _stab = stabData.get();

        if (_stab.getGpid() != null){
            JSONObject obj = new JSONObject(proxy.getDetails(_stab.getGpid()));
            _stab.setRating(obj.getJSONObject("result").getDouble("rating"));
            repository.save(_stab);
        }

    }


}
