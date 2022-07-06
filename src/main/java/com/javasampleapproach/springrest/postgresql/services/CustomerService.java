package com.javasampleapproach.springrest.postgresql.services;

import com.javasampleapproach.springrest.postgresql.model.Customer;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    @Autowired
    CustomerRepository repository;

    public List<Customer> getCustomers(){

        return (List<Customer>) repository.findAll();
    }

    public Customer createCustomer(Customer user) {

        Customer _customer = repository.save(new Customer(user.getName(), user.getEmail()));
        return repository.save(_customer);
    }

    public String deleteCustomer(Long id) {

        repository.deleteById(id);

        return "Utente cancellato con successo!";
    }

    public String deleteAllCustomers(){

        repository.deleteAll();
        return "Tutti gli utenti sono stati cancellati con successo!";
    }

    public Customer getCustomer(Long id){

        Customer user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Utente non presente"));

        return user;
    }

    public Customer saveCustomer(Customer user) {

        return repository.save(user);
    }


}
