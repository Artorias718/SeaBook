package com.javasampleapproach.springrest.postgresql;

import com.javasampleapproach.springrest.postgresql.Utils.Utils;
import com.javasampleapproach.springrest.postgresql.model.Spot;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.services.SpotService;
import com.javasampleapproach.springrest.postgresql.services.StabilimentoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpotControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SpotService service;

    @Test
    public void contextLoads() {
    }

    @Test
    public void givenSpots_whenGetSpots_thenReturnJsonArray() throws Exception {

        Spot s1 = new Spot(1,20);
        Spot s2 = new Spot(1,25);
        Spot s3 = new Spot(2,22);

        List<Spot> allSpots = Arrays.asList(s1,s2);

        given(service.getAllSpots(1)).willReturn(allSpots);

        mvc.perform(get("/api/v1/stabilimenti/{sid}/lista_Posti",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].price", is(s1.getPrice())))
                .andExpect(jsonPath("$[0].stabId", is(((int) s1.getStabId()))))
                .andExpect(jsonPath("$[0].datePrenotate", is(s1.getDatePrenotate())));

        //TODO aggiungere le date e testarle
        verify(service, VerificationModeFactory.times(1)).getAllSpots(1);
        reset(service);

    }
    @Test
    public void givenSpot_whenGetSpot_thenReturnSpot() throws Exception {

        Spot s1 = new Spot(1,20);

        given(service.getSpot(1L)).willReturn(Optional.of(s1));

        mvc.perform(get("/api/v1/spots/{pid}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(s1.getPrice())))
                .andExpect(jsonPath("$.stabId", is(((int) s1.getStabId()))))
                .andExpect(jsonPath("$.datePrenotate", is(s1.getDatePrenotate())));

    }
    @Test
    public void givenSpotWithBookedDates_whenGetSpot_thenReturnSpot() throws Exception {

        Spot s1 = new Spot(1,20);
        List<Date> date = new ArrayList<>();
        date.add(Utils.extractAndFormatDate("2022-06-20",""));
        date.add(Utils.extractAndFormatDate("2022-06-21",""));

        s1.setDatePrenotate(date);

        given(service.getSpot(1L)).willReturn(Optional.of(s1));

        mvc.perform(get("/api/v1/spots/{pid}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(s1.getPrice())))
                .andExpect(jsonPath("$.stabId", is(((int) s1.getStabId()))))
                .andExpect(jsonPath("$.datePrenotate", hasSize(2)))
                .andExpect(jsonPath("$.datePrenotate[0]", is("2022-06-19"+"T22:00:00.000+0000")))
                .andExpect(jsonPath("$.datePrenotate[1]", is("2022-06-20"+"T22:00:00.000+0000")));

    }
    @Test
    public void whenPostSpot_thenCreateSpot() throws Exception {

        Spot s1 = new Spot(1,20);

        given(service.createSpot(Mockito.any(),Mockito.any())).willReturn(s1);

        mvc.perform(post("/api/v1/stabilimenti/{sid}/create_spot",s1.getStabId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.toJson(s1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price", is(s1.getPrice())))
                .andExpect(jsonPath("$.stabId", is(((int) s1.getStabId()))))
                .andExpect(jsonPath("$.datePrenotate", is(s1.getDatePrenotate())));

        verify(service, VerificationModeFactory.times(1)).createSpot(Mockito.any(),Mockito.any());
        reset(service);


    }
    @Test
    public void givenSpots_whenDeleteAllSpotsOfStab_thenDeleteStabs() throws Exception {

        Spot s1 = new Spot(1,20);

        given(service.deleteAllSpotsInStab(1L)).willReturn("Spots cancellati con successo!");

        mvc.perform(delete("/api/v1/stabilimenti/{sid}/delete_spots",s1.getStabId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Spots cancellati con successo!")));

    }
    @Test
    public void givenSpot_whenDeleteSpot_thenDeleteSpot() throws Exception {

        given(service.deleteSpot(1L)).willReturn("Spot cancellato con successo!");

        mvc.perform(delete("/api/v1/stabilimenti/{sid}/{pid}/delete",1L,1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Spot cancellato con successo!")));
    }


}
