package com.javasampleapproach.springrest.postgresql;

import com.javasampleapproach.springrest.postgresql.model.Spot;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.services.SpotService;
import com.javasampleapproach.springrest.postgresql.services.StabilimentoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        //TODO aggiungere le date e testarle, dopo modifica con calendar o localDate
        verify(service, VerificationModeFactory.times(1)).getAllSpots(1);
        reset(service);

    }

    //********************STILLTODO********************
    @Test
    public void givenSpot_whenGetSpot_thenReturnSpot() throws Exception {


    }

    @Test
    public void whenPostSpot_thenCreateSpot() throws Exception {


    }

    @Test
    public void givenSpots_whenDeleteAllSpotsOfStab_thenDeleteStabs() throws Exception {
//fare anche il check che non cancella posti che non sono dello stabilimento di cui si stanno cancellando

    }

    @Test
    public void givenSpot_whenDeleteSpot_thenDeleteSpot() throws Exception {


    }

    @Test
    public void givenSpot_whenPutSpots_thenReturnModifiedSpot() throws Exception {


    }


//TODO testing degli errori (404, ecc)

    /*
    1. quando uno stabilimento viene cancellato, i suoi posti non ci devono essere
    2.


     */


}
