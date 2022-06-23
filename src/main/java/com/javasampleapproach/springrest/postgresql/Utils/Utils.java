package com.javasampleapproach.springrest.postgresql.Utils;

import com.javasampleapproach.springrest.postgresql.Proxy;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.util.Optional;

public class Utils {

    public static void updateRating(long id, StabilimentoRepository repository, Proxy proxy) throws JSONException {

        Optional<Stabilimento> stabData = repository.findById(id);
        Stabilimento _stab = stabData.get();

        if (_stab.getGpid() != null){
            JSONObject obj = new JSONObject(proxy.getDetails(_stab.getGpid()));
            _stab.setRating(obj.getJSONObject("result").getDouble("rating"));
            repository.save(_stab);
        }

    }

    public static byte[] toJson(Object object) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsBytes(object);
        }

}
