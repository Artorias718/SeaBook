package com.javasampleapproach.springrest.postgresql.repo;

import com.javasampleapproach.springrest.postgresql.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomerDatabaseLoader implements CommandLineRunner {

    private final CustomerRepository repository;

    @Autowired
    public CustomerDatabaseLoader(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(new Customer("Carlo", 12));
        repository.save(new Customer("Silvia", 12));
        repository.save(new Customer("Sara", 12));
        repository.save(new Customer("Antonio", 62));
        repository.save(new Customer("Paola", 62));
        repository.save(new Customer("Marco", 52));
        repository.save(new Customer("Andrea", 25));
        repository.save(new Customer("Matteo", 25));
    }
}
