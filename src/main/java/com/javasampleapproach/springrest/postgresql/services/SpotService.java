package com.javasampleapproach.springrest.postgresql.services;

import com.javasampleapproach.springrest.postgresql.Utils.Utils;
import com.javasampleapproach.springrest.postgresql.model.Spot;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.SpotRepository;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.javasampleapproach.springrest.postgresql.Utils.Utils.extractAndFormatDate;

@Service
@RequiredArgsConstructor
public class SpotService {

    @Autowired
    private final SpotRepository spotRepository;

    @Autowired
    private final StabilimentoRepository stabRepository;

    public Spot createSpot(Long sid, Spot spot){

        Spot newspot = spotRepository.save(new Spot(sid, spot.getPrice()));
        Stabilimento stab = stabRepository.findById(sid).orElseThrow(() -> new EntityNotFoundException(
                "Stabilimeto con id " + sid + " inesistente!"));

        stab.increaseCapacity();
        stabRepository.save(stab);

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

        stabRepository.findById(sid).orElseThrow(() -> new EntityNotFoundException(
                "Stabilimeto con id " + sid + " inesistente!"));

        List<Spot> posti = new ArrayList<>();
        posti.addAll(spotRepository.findAllBySid(sid));

        return posti;
    }

    public List<Spot> getAllSpotsFlagged(long sid, String selectedDate) throws JSONException {

        List<Spot> posti = new ArrayList<>(spotRepository.findAllBySid(sid));

        for (Spot s : posti) {
            List<Date> datesList = s.getDatePrenotate();
            if (datesList.contains(Utils.extractAndFormatDate(selectedDate, ""))) {
                s.IsBooked(true);
            }
        }

        return posti;
    }

    @Transactional
    @RabbitListener(queues = "bookingQueue")
    public void listener(String message) throws JSONException {

        JSONObject obj = new JSONObject(message);

        JSONArray listaPosti = obj.getJSONArray("listaPosti");
        String dataPrenotMsg = obj.getString("dataPrenotazione");

        for (int i = 0; i < listaPosti.length(); i++) {

            Optional<Spot> spot = spotRepository.findById(listaPosti.getLong(i));

            if (spot.isPresent()) {
                Spot _spot = spot.get();
                List<Date> datePrenotate = _spot.getDatePrenotate();
                datePrenotate.add(extractAndFormatDate(dataPrenotMsg,"booking"));
                _spot.setDatePrenotate(datePrenotate);

                spotRepository.save(_spot);
            }
        }
    }

    @Transactional
    @RabbitListener(queues = "debookingQueue")
    public void listener2(String message) throws JSONException {

        JSONObject obj = new JSONObject(message);

        JSONArray listaPosti = obj.getJSONArray("listaPosti");
        String dataPrenotMsg = obj.getString("dataPrenotazione");

        for (int i = 0; i < listaPosti.length(); i++) {

            Optional<Spot> spot = spotRepository.findById(listaPosti.getLong(i));

            if (spot.isPresent()) {
                Spot _spot = spot.get();
                List<Date> datePrenotate = _spot.getDatePrenotate();
                datePrenotate.remove(extractAndFormatDate(dataPrenotMsg,"debooking"));
                _spot.setDatePrenotate(datePrenotate);
                spotRepository.save(_spot);
            }
        }

    }


}
