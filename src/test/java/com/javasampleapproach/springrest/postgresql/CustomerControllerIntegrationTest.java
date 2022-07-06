package com.javasampleapproach.springrest.postgresql;

import com.javasampleapproach.springrest.postgresql.Utils.Utils;
import com.javasampleapproach.springrest.postgresql.model.Customer;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.services.CustomerService;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService service;

    @Test
    public void contextLoads() {
    }

    @Test
    public void givenCustomers_whenGetCustomers_thenReturnJsonArray() throws Exception {

        Customer c1 = new Customer("Andrea", "seabok2022@gmail.com");
        Customer c2 = new Customer("Alessio", "alex@gmail.com");


        List<Customer> allCustomers = Arrays.asList(c1,c2);

        given(service.getCustomers()).willReturn(allCustomers);

        mvc.perform(get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(c1.getName())))
                .andExpect(jsonPath("$[0].email", is(c1.getEmail())))
                .andExpect(jsonPath("$[1].name", is(c2.getName())))
                .andExpect(jsonPath("$[1].email", is(c2.getEmail())));

        verify(service, VerificationModeFactory.times(1)).getCustomers();
        reset(service);

    }

    @Test
    public void whenPostCustomer_thenCreateCustomer() throws Exception {


        Customer c1 = new Customer("Andrea", "seabok2022@gmail.com");

        given(service.createCustomer(Mockito.any())).willReturn(c1);

        mvc.perform(post("/api/v1/customers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.toJson(c1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(c1.getName())))
                .andExpect(jsonPath("$.email", is(c1.getEmail())));

        verify(service, VerificationModeFactory.times(1)).createCustomer(Mockito.any());
        reset(service);

    }

    @Test
    public void givenStabs_whenDeleteStabs_thenDeleteStabs() throws Exception {
        given(service.deleteAllCustomers()).willReturn("Tutti gli utenti sono stati cancellati con successo!");

        mvc.perform(delete("/api/v1/customers/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Tutti gli utenti sono stati cancellati con successo!")));

    }

}
