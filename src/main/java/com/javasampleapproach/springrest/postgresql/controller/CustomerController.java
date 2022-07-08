package com.javasampleapproach.springrest.postgresql.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.javasampleapproach.springrest.postgresql.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javasampleapproach.springrest.postgresql.model.Customer;
import com.javasampleapproach.springrest.postgresql.repo.CustomerRepository;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4000"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CustomerController {

	@Autowired
	CustomerRepository repository;

	private final CustomerService customerService;

	@GetMapping("/user/me")
	public Map<String, Object> userDetails(@AuthenticationPrincipal OAuth2User user) {
		user.getAttributes();
		return user.getAttributes();
	}

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomers() {

		return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
	}

	@PostMapping(value = "/customers/create")
	public ResponseEntity<Customer> postCustomer(@RequestBody Customer customer) {

		return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {

		return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);

	}

	@DeleteMapping("/customers/delete")
	public ResponseEntity<String> deleteAllCustomers() {

		return new ResponseEntity<>(customerService.deleteAllCustomers(), HttpStatus.OK);
	}

	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
		System.out.println("Update Customer with ID = " + id + "...");

		Optional<Customer> customerData = Optional.ofNullable(customerService.getCustomer(id));

		if (customerData.isPresent()) {
			Customer _customer = customerData.get();
			_customer.setName(customer.getName());
			return new ResponseEntity<>(customerService.saveCustomer(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
