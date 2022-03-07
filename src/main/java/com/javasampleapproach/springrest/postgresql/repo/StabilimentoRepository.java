package com.javasampleapproach.springrest.postgresql.repo;

import java.util.List;

import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import org.springframework.data.repository.CrudRepository;

import com.javasampleapproach.springrest.postgresql.model.Customer;

public interface StabilimentoRepository extends CrudRepository<Stabilimento, Long> {

    Stabilimento findById(long Id);

}

