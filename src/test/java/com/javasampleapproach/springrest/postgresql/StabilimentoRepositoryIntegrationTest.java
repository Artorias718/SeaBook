package com.javasampleapproach.springrest.postgresql;

import java.util.List;
import java.util.Optional;

import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StabilimentoRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StabilimentoRepository repository;

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void whenFindById_thenReturnStabilimento() {
        Stabilimento s1 = new Stabilimento("Acquamarina",10,"Via Boito 65 CA","07341232984",null,0,null);
        entityManager.persistAndFlush(s1);

        Optional<Stabilimento> found = repository.findById(s1.getId());
        assertThat(found.get().getId()).isEqualTo(s1.getId());
        assertThat(found.get().getName()).isEqualTo(s1.getName());
        assertThat(found.get().getAddress()).isEqualTo(s1.getAddress());
        assertThat(found.get().getPhoneNumber()).isEqualTo(s1.getPhoneNumber());
        assertThat(found.get().getSpotsNumber()).isEqualTo(s1.getSpotsNumber());
        assertThat(found.get().getGpid()).isEqualTo(s1.getGpid());
        assertThat(found.get().getRating()).isEqualTo(s1.getRating());






        //Fare stabilimento completo
    }



}
