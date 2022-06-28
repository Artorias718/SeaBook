package com.javasampleapproach.springrest.postgresql;

import com.javasampleapproach.springrest.postgresql.model.Spot;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.SpotRepository;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SpotRepositoryIntegrationTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SpotRepository repository;

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void whenFindAllBySid_thenReturnSpots() {
        Spot s1 = new Spot(1,20.00);
        Spot s2 = new Spot(1,40.00);

        Date d1 = new Date();
        Date d2 = new Date();

        List<Date> bookedDates = new ArrayList<>();
        bookedDates.add(d1);
        bookedDates.add(d2);

        s1.setDatePrenotate(bookedDates);
        s2.setDatePrenotate(bookedDates);

        entityManager.persistAndFlush(s1);
        entityManager.persistAndFlush(s2);

        List<Spot> found = repository.findAllBySid(s1.getStabId());

        assertThat(found.get(0).getStabId()).isEqualTo(s1.getStabId());
        assertThat(found.get(0).getPrice()).isEqualTo(s1.getPrice());
        assertThat(found.get(0).getDatePrenotate()).isEqualTo(s1.getDatePrenotate());
        assertThat(found.get(0).getDatePrenotate()).size().isEqualTo(s1.getDatePrenotate().size());
        assertThat(found.get(1).getStabId()).isEqualTo(s2.getStabId());
        assertThat(found.get(1).getPrice()).isEqualTo(s2.getPrice());
        assertThat(found.get(1).getDatePrenotate()).isEqualTo(s2.getDatePrenotate());
        assertThat(found.get(1).getDatePrenotate()).size().isEqualTo(s2.getDatePrenotate().size());

    }

    @Test
    public void whenDeleteAllBySid_thenDeleteSpots() {
        Spot s1 = new Spot(1,20.00);
        Spot s2 = new Spot(1,40.00);

        Date d1 = new Date();
        Date d2 = new Date();

        List<Date> bookedDates = new ArrayList<>();
        bookedDates.add(d1);
        bookedDates.add(d2);

        s1.setDatePrenotate(bookedDates);
        s2.setDatePrenotate(bookedDates);

        entityManager.persistAndFlush(s1);
        entityManager.persistAndFlush(s2);

        repository.deleteAllBySid(1);

        List<Spot> found = repository.findAllBySid(s1.getStabId());

        assertThat(found).isEmpty();

    }



}
