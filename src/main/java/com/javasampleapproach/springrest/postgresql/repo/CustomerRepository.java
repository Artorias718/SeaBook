package com.javasampleapproach.springrest.postgresql.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.javasampleapproach.springrest.postgresql.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

// per dare accesso cors a spring-boot-starter-data-rest
@CrossOrigin() // da accesso a tutti, non molto interessante ma funziona
@RepositoryRestResource
//
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
	List<Customer> findByAge(int age);


}

