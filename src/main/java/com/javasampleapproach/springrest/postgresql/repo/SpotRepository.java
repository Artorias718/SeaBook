package com.javasampleapproach.springrest.postgresql.repo;

import com.javasampleapproach.springrest.postgresql.model.Spot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpotRepository extends CrudRepository<Spot, Long> {

    List<Spot> findAllBySid(long sid);






}
