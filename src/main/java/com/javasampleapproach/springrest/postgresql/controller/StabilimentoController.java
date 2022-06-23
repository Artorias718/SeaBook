package com.javasampleapproach.springrest.postgresql.controller;

import java.util.List;
import java.util.Optional;

import com.javasampleapproach.springrest.postgresql.Proxy;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import com.javasampleapproach.springrest.postgresql.services.StabilimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StabilimentoController {

    @Autowired
    StabilimentoRepository repository;

    @Autowired
    Proxy proxy;

    private final StabilimentoService stabilimentoService;

    @GetMapping("/google/{placeId}")
    public String getDetails(@PathVariable String placeId) throws JSONException {

        JSONObject obj = new JSONObject(proxy.getDetails(placeId));
        String a = obj.getJSONObject("result").getString("name");
        System.out.println(a);

        return proxy.getDetails(placeId);

    }

    @GetMapping("/stabilimenti")
    public ResponseEntity<List<Stabilimento>> getAllStabilimenti() throws JSONException {

        return new ResponseEntity<>(stabilimentoService.getStabilimenti(), HttpStatus.OK);
    }

    @PostMapping(value = "/stabilimenti/create")
    public ResponseEntity<Stabilimento> createStabilimento(@RequestBody Stabilimento stabilimento) {

        return new ResponseEntity<>(stabilimentoService.createStabilimento(stabilimento), HttpStatus.CREATED);
    }

    @PostMapping(value = "/stabilimenti/create/{placeId}")
    public ResponseEntity<Stabilimento> postStabilimentoByGoogle(@PathVariable String placeId) throws JSONException {

        return new ResponseEntity<>(stabilimentoService.createStabWithGoogle(placeId), HttpStatus.CREATED);
    }

    @GetMapping("/stabilimenti/{id}")
    public ResponseEntity<Optional<Stabilimento>> getStabilimento(@PathVariable long id) throws JSONException {

        return new ResponseEntity<>(Optional.ofNullable(stabilimentoService.getStabilimento(id)), HttpStatus.OK);

    }

    @DeleteMapping("/stabilimenti/{id}/delete")
    public ResponseEntity<String> deleteStabilimento(@PathVariable("id") long id) throws JSONException {

        return new ResponseEntity<>(stabilimentoService.deleteStabilimento(id), HttpStatus.OK);
    }

    @DeleteMapping("/stabilimenti/delete")
    public ResponseEntity<String> deleteAllStabilimenti() throws JSONException {

        return new ResponseEntity<>(stabilimentoService.deleteAllStabilimenti(), HttpStatus.OK);
    }

    @PutMapping("/stabilimenti/{id}/put")
    public ResponseEntity<Stabilimento> updateStabilimento(@PathVariable("id") long id, @RequestBody Stabilimento stabilimento) throws JSONException {

        Optional<Stabilimento> stabData = Optional.ofNullable(stabilimentoService.getStabilimento(id));

        if (stabData.isPresent()) {
            Stabilimento _stab = stabData.get();
            _stab.setName(stabilimento.getName());
            _stab.setSpotsNumber(stabilimento.getSpotsNumber());
            _stab.setAddress(stabilimento.getAddress());
            _stab.setPhoneNumber(stabilimento.getPhoneNumber());
            _stab.setGpid(stabilimento.getGpid());
            _stab.setRating(stabilimento.getRating());
            return new ResponseEntity<>(stabilimentoService.createStabilimento(_stab), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //TODO spostare logica di business in @Service, solamente se da tenere
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

}
