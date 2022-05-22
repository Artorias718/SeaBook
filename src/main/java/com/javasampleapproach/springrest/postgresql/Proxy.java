package com.javasampleapproach.springrest.postgresql;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="quote-service", url = "https://maps.googleapis.com/maps/api")
public interface Proxy {

    String GoogleKey = "AIzaSyAk5gXXtzL3bDr--V7jI71K42Bb1Yp7fwY";

    @GetMapping("/place/details/json?place_id={placeId}&key="+GoogleKey+"&fields=name,formatted_address,formatted_phone_number,place_id,rating")
    String getDetails(@PathVariable String placeId);
}
