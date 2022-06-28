package com.javasampleapproach.springrest.postgresql.services;

import com.javasampleapproach.springrest.postgresql.model.Spot;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.SpotRepository;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpotService {

    @Autowired
    private final SpotRepository spotRepository;

    @Autowired
    private final StabilimentoRepository stabRepository;

    public Spot createSpot(Long sid, Spot spot){

        Spot newspot = spotRepository.save(new Spot(sid, spot.getPrice()));
        Optional<Stabilimento> stab = stabRepository.findById(sid);

        if (stab.isPresent()) {
            Stabilimento s = stab.get();
            s.increaseCapacity();
            stabRepository.save(s);
        }

        return newspot;

    }

    public Optional<Spot> getSpot(Long pid){
        return spotRepository.findById(pid);
    }

    public String deleteAllSpotsInStab(long sid){

        spotRepository.deleteAllBySid(sid);

        return "Tutti gli spots sono stati cancellati con successo!";

        //TODO Decrementare il numero posti dello stabilimento


    }

    public String deleteSpot(long pid){

        spotRepository.deleteById(pid);
        //TODO Decrementare il numero posti dello stabilimento
        return "spot cancellato con successo!";

    }

    public List<Spot> getAllSpots(long sid){

        List<Spot> posti = new ArrayList<>();
        posti.addAll(spotRepository.findAllBySid(sid));

        return posti;
    }




}
