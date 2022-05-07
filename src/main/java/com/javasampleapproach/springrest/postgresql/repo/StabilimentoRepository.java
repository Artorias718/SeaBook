package com.javasampleapproach.springrest.postgresql.repo;

import java.util.List;

import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import org.springframework.data.repository.CrudRepository;


public interface StabilimentoRepository extends CrudRepository<Stabilimento, Long> {

}

