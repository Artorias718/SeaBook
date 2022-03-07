package com.javasampleapproach.springrest.postgresql.repo;

import com.javasampleapproach.springrest.postgresql.model.Posto;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostoRepository extends CrudRepository<Posto, Long> {

    List<Posto> findAllBySid(long sid);






}
