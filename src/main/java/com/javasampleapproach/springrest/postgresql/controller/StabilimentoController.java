package com.javasampleapproach.springrest.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class StabilimentoController {

    @Autowired
    StabilimentoRepository repository;

    @GetMapping("/stabilimenti")
    public List<Stabilimento> getAllStabilimenti() {

        List<Stabilimento> stabilimenti = new ArrayList<>();
        repository.findAll().forEach(stabilimenti::add);

        return stabilimenti;
    }

    // questo non potra essere invocato da tutti
//    @PostMapping(value = "/stabilimenti/create")
//    public Stabilimento postStabilimento(@RequestBody Stabilimento stabilimento) {
//
//        Stabilimento newstab = repository.save(
//                new Stabilimento(
//                        stabilimento.getName(),
//                        stabilimento.getSpotsNumber(),
//                        stabilimento.getAddress(),
//                        stabilimento.getPhoneNumber()));
//
//        return newstab;
//    }

    @GetMapping("/stabilimenti/{id}")
    public Optional<Stabilimento> getStabilimento(@PathVariable long id){

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
            return new ResponseEntity<>(repository.save(_stab), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    public List<Stabilimento> search()

    /*@PostMapping(value = "/stabilimenti/{id}/addPosto")
    public void postPostoInStabilimento(@RequestBody Spot posto, @PathVariable("id") long id) {

        //manca dire a quale stabilimento, tramite l'id
        Spot _posto = lista_posti.save(new Spot());
    }*/

}
