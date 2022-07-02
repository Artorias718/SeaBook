package com.javasampleapproach.springrest.postgresql.services;

import com.javasampleapproach.springrest.postgresql.Proxy;
import com.javasampleapproach.springrest.postgresql.Utils.Utils;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StabilimentoService {

    @Autowired
    private final StabilimentoRepository repository;

    @Autowired
    Proxy proxy;

    public Stabilimento createStabilimento(Stabilimento stabilimento) {

        Stabilimento stab = new Stabilimento(
                stabilimento.getName(),
                stabilimento.getSpotsNumber(),
                stabilimento.getAddress(),
                stabilimento.getPhoneNumber(),
                stabilimento.getGpid(),
                stabilimento.getRating());

        return repository.save(stab);
    }

    public Stabilimento getStabilimento(Long id) throws JSONException {

        Stabilimento stab = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Stabilimento non presente"));

        Utils.updateRating(id, repository, proxy);

        return stab;
    }

    public List<Stabilimento> getStabilimenti(){

        return (List<Stabilimento>) repository.findAll();
    }

    public Stabilimento createStabWithGoogle(String placeId) throws JSONException {

        JSONObject obj = new JSONObject(proxy.getDetails(placeId));
        JSONArray photos = obj.getJSONObject("result").getJSONArray("photos");
        int length = photos.length();
        for (int i = 0; i < length; i++) {
            JSONObject photo = photos.getJSONObject(i);
            System.out.println(photo.get("photo_reference"));
        }
        JSONObject firstPhoto = photos.getJSONObject(0);
        System.out.println("firstPhoto: " + firstPhoto.get("photo_reference"));
        String photo_reference0 = firstPhoto.getString("photo_reference");

        Stabilimento newstab = repository.save(
                new Stabilimento(
                        obj.getJSONObject("result").getString("name"),
                        0,
                        obj.getJSONObject("result").getString("formatted_address"),
                        obj.getJSONObject("result").getString("formatted_phone_number"),
                        obj.getJSONObject("result").getString("place_id"),
                        obj.getJSONObject("result").getDouble("rating"),
                        photo_reference0
                ));
        return newstab;
    }

    public String deleteStabilimento(Long id) throws JSONException {

        repository.deleteById(id);

        return "Stabilimento cancellato con successo!";
    }

    public String deleteAllStabilimenti() throws JSONException {

        repository.deleteAll();
        return "Tutti gli stabilimenti sono stati cancellati con successo!";
    }

}
