package com.javasampleapproach.springrest.postgresql;

import com.javasampleapproach.springrest.postgresql.model.Customer;
import com.javasampleapproach.springrest.postgresql.model.Spot;
import com.javasampleapproach.springrest.postgresql.repo.CustomerRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository repository;

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void whenFindCustomerByName_thenReturnCustomer() {
        Customer c1 = new Customer("Andrea", "seabok2022@gmail.com");

        entityManager.persistAndFlush(c1);

        Customer found = repository.findByName(c1.getName());

        assertThat(found.getName()).isEqualTo(c1.getName());
        assertThat(found.getEmail()).isEqualTo(c1.getEmail());

    }

    @Test
    public void whenFindCustomerByEmail_thenReturnCustomer() {
        Customer c1 = new Customer("Andrea", "seabok2022@gmail.com");

        entityManager.persistAndFlush(c1);

        Customer found = repository.findByEmail(c1.getEmail());

        assertThat(found.getName()).isEqualTo(c1.getName());
        assertThat(found.getEmail()).isEqualTo(c1.getEmail());

    }
}
