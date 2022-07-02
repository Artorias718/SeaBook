package com.javasampleapproach.springrest.postgresql.Utils;

import com.javasampleapproach.springrest.postgresql.Proxy;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
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

    public static Date extractAndFormatDate(String date, String mode){


        SimpleDateFormat sdf3;

        if(Objects.equals(mode, "booking")) {
            sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        }
        else if(Objects.equals(mode, "debooking")){
            sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s", Locale.ENGLISH);
        }
        else{
            sdf3 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        }

        Date newDate = null;
        try{
            newDate = sdf3.parse(date);

        }catch (Exception e){ e.printStackTrace(); }

        return newDate;



    }

}
