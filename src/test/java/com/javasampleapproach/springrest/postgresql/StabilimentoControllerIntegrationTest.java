package com.javasampleapproach.springrest.postgresql;

import com.javasampleapproach.springrest.postgresql.Utils.Utils;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.services.StabilimentoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StabilimentoControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StabilimentoService service;

    @Test
    public void contextLoads() {
    }

    @Test
    public void givenStabs_whenGetStabs_thenReturnJsonArray() throws Exception {

        Stabilimento s1 = new Stabilimento("Acquamarina",10,"Via Boito 65 CA","07341232984",null,0,null);

        Stabilimento s2 = new Stabilimento("Baia Nera");

        List<Stabilimento> allStabs = Arrays.asList(s1,s2);


        given(service.getStabilimenti()).willReturn(allStabs);

        mvc.perform(get("/api/v1/stabilimenti")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(s1.getName())))
                .andExpect(jsonPath("$[0].spotsNumber", is(s1.getSpotsNumber())))
                .andExpect(jsonPath("$[0].address", is(s1.getAddress())))
                .andExpect(jsonPath("$[0].gpid", is(s1.getGpid())))
                .andExpect(jsonPath("$[0].rating", is(s1.getRating())))
                .andExpect(jsonPath("$[0].phoneNumber", is(s1.getPhoneNumber())))
                .andExpect(jsonPath("$[1].name", is(s2.getName())))
                .andExpect(jsonPath("$[1].spotsNumber", is(s2.getSpotsNumber())))
                .andExpect(jsonPath("$[1].address", is(s2.getAddress())))
                .andExpect(jsonPath("$[1].gpid", is(s2.getGpid())))
                .andExpect(jsonPath("$[1].rating", is(s2.getRating())))
                .andExpect(jsonPath("$[1].phoneNumber", is(s2.getPhoneNumber())));

        verify(service, VerificationModeFactory.times(1)).getStabilimenti();
        reset(service);

    }

    @Test
    public void givenStab_whenGetStab_thenReturnJson() throws Exception {

        Stabilimento s1 = new Stabilimento("Acquamarina",10,"Via Boito 65 CA","07341232984",null,0,null);

        given(service.getStabilimento(1L)).willReturn(s1);

        mvc.perform(get("/api/v1/stabilimenti/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(s1.getName())))
                .andExpect(jsonPath("$.spotsNumber", is(s1.getSpotsNumber())))
                .andExpect(jsonPath("$.address", is(s1.getAddress())))
                .andExpect(jsonPath("$.gpid", is(s1.getGpid())))
                .andExpect(jsonPath("$.rating", is(s1.getRating())))
                .andExpect(jsonPath("$.phoneNumber", is(s1.getPhoneNumber())));
    }

    @Test
    public void whenPostStab_thenCreateStab() throws Exception {

        Stabilimento s1 = new Stabilimento("Acquamarina",10,"Via Boito 65 CA","07341232984",null,0,null);

        given(service.createStabilimento(Mockito.any())).willReturn(s1);

        mvc.perform(post("/api/v1/stabilimenti/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.toJson(s1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Acquamarina")))
                .andExpect(jsonPath("$.spotsNumber", is(s1.getSpotsNumber())))
                .andExpect(jsonPath("$.address", is(s1.getAddress())))
                .andExpect(jsonPath("$.gpid", is(s1.getGpid())))
                .andExpect(jsonPath("$.rating", is(s1.getRating())))
                .andExpect(jsonPath("$.phoneNumber", is(s1.getPhoneNumber())));

        verify(service, VerificationModeFactory.times(1)).createStabilimento(Mockito.any());
        reset(service);

    }

    @Test
    public void whenPostStabWithGoogle_thenCreateStab() throws Exception {

        Stabilimento s1 = new Stabilimento("Baia Calipso",0,"Via Fabrizio de Andrè, 16011 Arenzano GE, Italy","348 037 3117","ChIJR9-OChc90xIR2g3iSA8n0qU",4,"Aap_uEDvc4IVs1I9VPNSviCZ7XyXQv4GB7Sr0zmjS_vNqUVRT9p-uPCO0MfpvLH3lPpUxgCin6bRFFNHkjMvfeqXe_jxCFaZDC6v4JWN9O3ipYcJSZqbLccwHlA36aoRdFPWVXD1Q7wRtTw5mDDcZWzRdGSWbi2eSB8Eu4fKsRl3r2AKXTia");

        given(service.createStabWithGoogle(Mockito.any())).willReturn(s1);

        mvc.perform(post("/api/v1/stabilimenti/create/{place_id}","ChIJR9-OChc90xIR2g3iSA8n0qU")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.toJson(s1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Baia Calipso")))
                .andExpect(jsonPath("$.spotsNumber", is(s1.getSpotsNumber())))
                .andExpect(jsonPath("$.address", is(s1.getAddress())))
                .andExpect(jsonPath("$.gpid", is(s1.getGpid())))
                .andExpect(jsonPath("$.rating", is(s1.getRating())))
                .andExpect(jsonPath("$.phoneNumber", is(s1.getPhoneNumber())));

        verify(service, VerificationModeFactory.times(1)).createStabWithGoogle(Mockito.any());
        reset(service);
    }

    @Test
    public void givenStab_whenDeleteStab_thenDeleteStab() throws Exception {

        given(service.deleteStabilimento(1L)).willReturn("Stabilimento cancellato con successo!");

        mvc.perform(delete("/api/v1/stabilimenti/{id}/delete",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Stabilimento cancellato con successo!")));


    }

    @Test
    public void givenStabs_whenDeleteStabs_thenDeleteStabs() throws Exception {
        given(service.deleteAllStabilimenti()).willReturn("Tutti gli stabilimenti sono stati cancellati con successo!");

        mvc.perform(delete("/api/v1/stabilimenti/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Tutti gli stabilimenti sono stati cancellati con successo!")));

    }

//TODO testing degli errori (404, ecc)

    /*
    1. quando chiedo uno stabilimento che non c'è, errore
    2. quando cancello uno stabilimento che già non esiste, errore
    3.


     */




}
